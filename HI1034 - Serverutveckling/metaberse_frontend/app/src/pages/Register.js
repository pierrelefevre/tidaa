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

const Register = (props) => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    var handleSubmit = (e) => {
        e.preventDefault();
        console.log(props);
        console.log("I want to register! " + username + " " + password);
        setLoading(true);
        axios.post(API_URL.user + '/users', {
            username: username,
            password: password,
            firstname: firstName,
            lastname: lastName

        })
            .then(function (response) {
                console.log(response);
                setLoading(false);
                if (response.status === 201) {
                    console.log("Successfully registered in!");
                    navigate('/login');
                }
                else {
                    setError("Failed to login");
                }
            })
            .catch(function (error) {
                setLoading(false);
                setError(error.response.data.toString());
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
                    Register
                </Typography >
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        fullWidth
                        id="firstName"
                        label="First name"
                        name="firstName"
                        autoComplete="firstName"
                        autoFocus
                        value={firstName}
                        onInput={e => setFirstName(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        fullWidth
                        id="lastName"
                        label="Last name"
                        name="lastName"
                        autoComplete="lastName"
                        value={lastName}
                        onInput={e => setLastName(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        fullWidth
                        id="username"
                        label="Username"
                        name="username"
                        autoComplete="username"
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
                        Register
                    </LoadingButton>
                    <Grid container sx={{ display: "flex", justifyContent: "space-around" }}>
                        <MuiLink component={Link} to="/login" variant="body2" style={{ textDecoration: "none" }} >
                            Already have an account? Sign in
                        </MuiLink>
                    </Grid>
                </Box>
                {renderError()}
            </Box >
        </Container >
    )
}

export default Register;