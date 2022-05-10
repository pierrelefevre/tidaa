import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from 'react';
import { Typography, Container } from "@mui/material";
import Skeleton from '@mui/material/Skeleton';
import Alert from '@mui/material/Alert';
import Post from "../components/Post";
import axios from 'axios';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import CreatePost from '../components/CreatePost';
import MuiLink from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import userCache from '../userCache.js'
import API_URL from "../API_URL";

const Profile = (props) => {
    const [error, setError] = useState('');
    const [posts, setPosts] = useState('');
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    let { username } = useParams();

    const loadPosts = () => {
        axios.get(API_URL.post + "/posts/owner/" + user.id, { token: props.user.token })
            .then(function (response) {
                if (response.status === 200) {
                    for (var i = 0; i < response.data.length; i++) {
                        response.data[i].owner = userCache.getById(response.data[i].owner);
                    }
                    setPosts(response.data);

                    setError("");
                    setLoading(false);
                }
                else {
                    setError("Failed to load: " + response.data.error);
                    setLoading(false);
                    return null;
                }
            })
            .catch(function (error) {
                setLoading(false);
                if (error.response)
                    setError(error.response.data.toString());
                else
                    setError(error.toString());
                return null;
            });
    }

    var compare = (a, b) => {
        if (a.timestamp < b.timestamp) {
            return 1;
        }
        if (a.timestamp > b.timestamp) {
            return -1;
        }
        return 0;
    }

    useEffect(() => {
        const newUser = userCache.getByUsername(username)
        setUser(newUser)
        // eslint-disable-next-line
    }, [username]);

    useEffect(() => {
        if (user != null) {
            loadPosts()
            const refreshPosts = setInterval(function () { loadPosts(); }, 1000);
            return () => clearInterval(refreshPosts);
        }
        // eslint-disable-next-line
    }, [user]);


    const renderPosts = () => {
        if (loading) {
            return (<Skeleton variant="rectangular" height={118} />);
        }
        if (posts != null && posts.length > 0) {
            posts.sort(compare);
            var postList = posts.map((post, i) => {
                var timestamp = post.timestamp.split("T");
                timestamp[1] = timestamp[1].slice(0, -13);
                if (post.owner != null)
                    return <Post key={i} title={post.owner.firstname + " " + post.owner.lastname} type={post.type} username={post.owner.username} image={post.owner.imageUrl} content={post.content} timestamp={post.timestamp} />
                return <Skeleton variant="rectangular" height={180} sx={{ mb: 2 }} />
            });
            return postList;
        }
        return null;
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

    const fabStyle = {
        margin: 0,
        top: 'auto',
        right: 20,
        bottom: 20,
        left: 'auto',
        position: 'fixed',
    };

    const modalStyle = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    return (
        <div>
            <Typography variant="h4" component="h1" gutterBottom>
                {"@" + username}
            </Typography>

            {user != null ? (<Typography variant="h6" component="h2" gutterBottom>
                {user.firstname + " " + user.lastname}
            </Typography>) : <Skeleton variant="rectangular" height={118} sx={{ pb: 2 }} />}

            {!loading && parseInt(user.id) === parseInt(props.user.id) ? <Grid container sx={{ display: "flex", justifyContent: "space-around", mb: 3 }}>
                <MuiLink component={Link} to="/chooseavatar" variant="body2" style={{ textDecoration: "none" }} >
                    Change avatar
                </MuiLink>
            </Grid> : ""}


            {renderError()}
            <Container>
                {renderPosts()}

                {user != null && error === "" ? (
                    <Modal
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="modal-modal-title"
                        aria-describedby="modal-modal-description"
                    >
                        <Box sx={modalStyle}>
                            <CreatePost user={user} handleClose={handleClose} />
                        </Box>
                    </Modal>
                ) : ""}
                <Fab style={fabStyle} onClick={handleOpen} color="primary" aria-label="add">
                    <AddIcon />
                </Fab>
            </Container>
        </div>
    );
}

export default Profile;