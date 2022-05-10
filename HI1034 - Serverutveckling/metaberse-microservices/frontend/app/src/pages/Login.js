import { useState } from 'react';
import { useNavigate, Link } from "react-router-dom";
import axios from 'axios';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import MuiLink from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Alert from '@mui/material/Alert';
import LoadingButton from '@mui/lab/LoadingButton';
import API_URL from "../API_URL";

const Login = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    var handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        axios.post(API_URL.user + '/login', {
            username: username,
            password: password
        })
            .then(function (response) {
                console.log(response);
                setLoading(false);
                if (response.status === 200) {
                    props.setUser(response.data);
                    localStorage.setItem('firstname', response.data.firstname);
                    localStorage.setItem('lastname', response.data.lastname);
                    localStorage.setItem('id', response.data.id);
                    localStorage.setItem('username', response.data.username);
                    localStorage.setItem('imageURL', response.data.imageURL);
                    navigate('/');
                }
                else {
                    setError("Failed to login: " + response.data.error);
                }
            })
            .catch(function (error) {
                setLoading(false);
                setError(error.toString());
                //setError(error.response.data.toString());
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
                    Login
                </Typography >
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        fullWidth
                        id="username"
                        label="Username"
                        name="username"
                        autoComplete="username"
                        autoFocus
                        value={username}
                        onInput={e => setUsername(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                        value={password} onInput={e => setPassword(e.target.value)}
                    />

                    <LoadingButton
                        loading={loading}
                        variant="contained"
                        type="submit"
                        fullWidth
                        size="large"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Sign In
                    </LoadingButton>
                    <Grid container sx={{ display: "flex", justifyContent: "space-around" }}>
                        <MuiLink component={Link} to="/register" variant="body2" style={{ textDecoration: "none" }}>
                            Don't have an account? Register
                        </MuiLink>
                    </Grid>
                </Box>
                {renderError()}
            </Box >
        </Container >
    )
}

export default Login;