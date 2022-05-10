import { useState } from 'react';
import { useNavigate, Link } from "react-router-dom";
import axios from 'axios';
import CssBaseline from '@mui/material/CssBaseline';
import MuiLink from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Alert from '@mui/material/Alert';
import LoadingButton from '@mui/lab/LoadingButton';
import FormControl from '@mui/material/FormControl';
import userCache from '../userCache';
import API_URL from "../API_URL";

const ChooseAvatar = (props) => {
    const [file, setFile] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    var handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        console.log(file.name);
        const formData = new FormData();
        formData.append('file', file);
        formData.append("userID", props.user.id);
        formData.append("type", "PROFILE");
        formData.append("token", props.user.token);
        axios.post(
            API_URL.image + '/images',
            formData)
            .then(function (response) {
                console.log(response);
                setLoading(false);
                if (response.status === 201) {
                    setError("");
                    userCache.invalidate()
                    navigate('/');
                }
                else {
                    setError("Failed to upload. Try again");
                }
            })
            .catch(function (error) {
                setLoading(false);
                setError(error.toString());
            });
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

    var onFileChange = (e) => {
        setFile(e.target.files[0]);
        console.log("File changed " + e.target.files[0]);
    }

    return (
        <Container component="main" maxWidth="xs" >
            <CssBaseline />
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography variant="h4" component="h1" gutterBottom>
                    Choose avatar
                </Typography >
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <FormControl sx={{ mt: 3, ml: 2, mb: 1, width: "100%", display: "flex", alignItems: "center", justifyContent: "space-between", flexDirection: "row" }}>
                        Avatar <input id="profilePic" type="file" onChange={e => onFileChange(e)} />
                    </FormControl>


                    <LoadingButton
                        loading={loading}
                        variant="contained"
                        type="submit"
                        fullWidth
                        size="large"
                        sx={{ mt: 3, mb: 2 }}
                        disabled={!file}
                    >
                        Save avatar
                    </LoadingButton>
                    <Grid container sx={{ display: "flex", justifyContent: "space-around" }}>
                        <MuiLink variant="body2" component={Link} to="/" style={{ textDecoration: "none" }}>
                            Continue without avatar
                        </MuiLink>
                    </Grid>
                </Box>
                {renderError()}
            </Box >
        </Container >
    )
}

export default ChooseAvatar;