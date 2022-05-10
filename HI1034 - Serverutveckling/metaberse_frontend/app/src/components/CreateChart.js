import { useState } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Alert from '@mui/material/Alert';
import LoadingButton from '@mui/lab/LoadingButton';
import TextField from '@mui/material/TextField';
import API_URL from "../API_URL";

const CreateChart = (props) => {
    const [content, setContent] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    var submitChart = () => {
        var chartSubmitData = JSON.parse(content);
        chartSubmitData.user = props.user.id;

        axios.post(API_URL.chart + '/', { chartSubmitData })
            .then(function (response) {
                console.log(response);
                setLoading(false);

                axios.post(API_URL.chat + '/messages', {
                    chatId: props.conversation.chatId,
                    senderId: props.user.id,
                    content: response.data._id,
                    type: "CHART"
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
                        setError(JSON.stringify(error));
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
        submitChart()
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
                Create chart
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

                <LoadingButton
                    loading={loading}
                    variant="contained"
                    type="submit"
                    fullWidth
                    size="large"
                    sx={{ mt: 3, mb: 2 }}
                >
                    Create message
                </LoadingButton>
            </Box>
            {renderError()}
        </Box >
    )
}


export default CreateChart