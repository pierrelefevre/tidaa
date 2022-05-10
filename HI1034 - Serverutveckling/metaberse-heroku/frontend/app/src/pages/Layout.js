import { Outlet, Link } from "react-router-dom";

import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Home as HomeIcon, Login as LoginIcon, Logout as LogoutIcon, Chat as ChatIcon, AllInclusive as AppIcon, Face as FaceIcon, People as PeopleIcon } from '@mui/icons-material';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import AppBar from '@mui/material/AppBar';
import { Chip } from '@mui/material';
import { useNavigate } from "react-router-dom";
import IconButton from '@mui/material/IconButton';
import { blue, pink } from '@mui/material/colors';


const Layout = (props) => {
    const navigate = useNavigate();

    const theme = createTheme({
        palette: {
            mode: 'dark',
            primary: blue,
            secondary: pink,
        },
    });

    const logout = () => {
        localStorage.removeItem('user');
        props.setUser(null);
        navigate('/login');
    }

    const renderLogin = () => {
        if (props.user != null) {
            return (
                <IconButton size="large" onClick={() => logout()}>
                    <LogoutIcon />
                </IconButton>
            )
        } else {
            return (
                <Link to="/login">
                    <IconButton size="large">
                        <LoginIcon />
                    </IconButton>
                </Link>
            )
        }
    }

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <AppBar position="sticky">
                <Toolbar sx={{ display: "flex", alignItems: "center", justifyContent: "space-between" }}>
                    <Link to="/" style={{ textDecoration: "none", color: "white", display: "flex", alignItems: "center" }}>
                        <AppIcon sx={{ mr: 2 }} />
                        <Typography variant="h6" component="div" sx={{ textAlign: "start" }}>
                            MetaBerse
                        </Typography>
                    </Link>

                    <div>
                        <Link to="/">
                            <IconButton size="large">
                                <HomeIcon />
                            </IconButton>
                        </Link>

                        <Link to="/friends">
                            <IconButton size="large">
                                <PeopleIcon />
                            </IconButton>
                        </Link>


                        {props.user == null ? "" : <Link to="/chat">
                            <IconButton size="large">
                                <ChatIcon />
                            </IconButton>
                        </Link>}

                        {props.user == null ? "" : <Chip sx={{ mx: 1, textDecoration: "none", cursor: "pointer" }} icon={<FaceIcon />} label={"@" + props.user.username} component={Link} to={"/profile/" + props.user.username} />}

                        {renderLogin()}
                    </div>
                </Toolbar>
            </AppBar>
            <Container maxWidth="md" sx={{ my: 4 }}>
                <Outlet />
            </Container>
        </ThemeProvider>
    );
}
export default Layout;