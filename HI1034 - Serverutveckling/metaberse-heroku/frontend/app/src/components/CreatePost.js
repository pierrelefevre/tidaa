import { useState } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Alert from '@mui/material/Alert';
import LoadingButton from '@mui/lab/LoadingButton';
import TextField from '@mui/material/TextField';
import API_URL from "../API_URL";

const CreatePost = (props) => {
    const [content, setContent] = useState('');
    const [type, setType] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    var submitPost = () => {
        axios.post(API_URL.post + '/posts', {
            owner: props.user.id,
            content: content,
            type: type,
            timestamp: new Date().toISOString()
        })
            .then(function (response) {
                console.log(response);
                setLoading(false);
                if (response.status === 201) {
                    console.log("Successfully created post!");
                    setContent("");
                    props.handleClose();
                }
                else {
                    setError("Failed to post post");
                }
            })
            .catch(function (error) {
                setLoading(false);
                setError(error.response.data.toString());
            });
    }

    var submitChart = () => {
        var chartSubmitData = JSON.parse(content);
        chartSubmitData.user = props.user.id;

        axios.post(API_URL.chart + '/', { chartSubmitData })
            .then(function (response) {
                console.log(response);
                setLoading(false);

                axios.post(API_URL.post + '/posts', {
                    owner: props.user.id,
                    content: response.data._id,
                    type: "CHART",
                    timestamp: new Date().toISOString()
                })
                    .then(function (response) {
                        console.log(response);
                        setLoading(false);
                        if (response.status === 201) {
                            props.handleClose();
                        }
                        else {
                            setError("Failed to save chart as post");
                        }
                    })
                    .catch(function (error) {
                        setLoading(false);
                        setError(error.response.data.toString());
                    });
            })
            .catch(function (error) {
                setLoading(false);
                setError(JSON.stringify(error));
            });
    }

    var handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);

        switch (type) {
            case "TEXT":
                if (content.length !== 0) {
                    submitPost()
                }
                break
            case "CHART":
                if (content.length !== 0) {
                    submitChart()
                }
                break
            default:
                setError("Invalid post type");

        }
    }

    var renderError = () => {
        if (error) {
            return (
                <Alert sx={{ mt: 5 }} severity="error">{error}</Alert>
            )
        } else {
            return null;
        }
    }

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}
        >
            <Typography variant="h6" component="h6" gutterBottom>
                Create post
            </Typography >
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                <TextField
                    margin="normal"
                    fullWidth
                    id="content"
                    label="Content"
                    name="content"
                    autoComplete="content"
                    value={content}
                    onInput={e => setContent(e.target.value)}
                />
                <TextField
                    margin="normal"
                    fullWidth
                    id="type"
                    label="Type TEXT or CHART"
                    name="type"
                    autoComplete="type"
                    value={type}
                    onInput={e => setType(e.target.value)}
                />

                <LoadingButton
                    loading={loading}
                    variant="contained"
                    type="submit"
                    fullWidth
                    size="large"
                    sx={{ mt: 3, mb: 2 }}
                >
                    Create post
                </LoadingButton>
            </Box>
            {renderError()}
        </Box >
    )
}


export default CreatePost