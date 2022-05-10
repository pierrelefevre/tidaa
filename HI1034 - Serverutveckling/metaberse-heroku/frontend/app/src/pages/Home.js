import { useState, useEffect } from 'react';
import axios from 'axios';
import { Typography } from "@mui/material";
import { Container } from '@mui/material';
import Skeleton from '@mui/material/Skeleton';
import Alert from '@mui/material/Alert';
import Post from "../components/Post";
import CreatePost from '../components/CreatePost';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import userCache from '../userCache.js'
import API_URL from "../API_URL";


function Home(props) {
    const [error, setError] = useState('');
    const [posts, setPosts] = useState(null);
    const [loading, setLoading] = useState(true);
    const [friends, setFriends] = useState(null);
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const loadPosts = () => {

        var friendsArray = []
        for (var i = 0; i < friends.length; i++) {
            friendsArray.push(friends[i].id)
        }
        friendsArray.push(props.user.id);
        axios.post(API_URL.post + "/posts/owner/", {
            users: friendsArray
        })
            .then(function (response) {
                if (response.status === 200) {
                    for (let i = 0; i < response.data.length; i++) {
                        response.data[i].owner = userCache.getById(response.data[i].owner);
                    }

                    //if post doesnt already contain post with same id
                    // add post to posts
                    setPosts(response.data)

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

    const loadFriends = () => {
        var url = API_URL.user + "/users/" + props.user.id + "/friends";
        console.log(url);
        axios.get(url)
            .then(function (response) {
                if (response.status === 200) {
                    setFriends(response.data);
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

    useEffect(() => {
        if (props.user != null)
            setFriends(loadFriends());
        // eslint-disable-next-line
    }, [props.user]);

    useEffect(() => {
        const refreshPosts = setInterval(function () {
            if (friends != null) {
                loadPosts();
            }
        }, 750);
        return () => clearInterval(refreshPosts);
        // eslint-disable-next-line
    }, [friends, posts]);

    const renderPosts = () => {
        if (loading) {
            return (
                <div>
                    <Skeleton variant="rectangular" height={180} sx={{ mb: 2 }} />
                    <Skeleton variant="rectangular" height={180} sx={{ mb: 2 }} />
                    <Skeleton variant="rectangular" height={180} sx={{ mb: 2 }} />
                </div>
            );
        }
        if (posts != null && posts.length > 0) {
            posts.sort(compare);
            var postList = posts.map((post, i) => {
                if (post.owner != null) {
                    return <Post key={i} title={post.owner.firstname + " " + post.owner.lastname} type={post.type} username={post.owner.username} image={post.owner.imageUrl} content={post.content} timestamp={post.timestamp} />
                }
                return <Skeleton variant="rectangular" height={180} sx={{ mb: 2 }} />
            });
            return postList;
        }
        return null;
    }

    var compare = (a, b) => {
        if (a.id < b.id) {
            return 1;
        }
        if (a.id > b.id) {
            return -1;
        }
        return 0;
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
                Home
            </Typography>
            {renderError()}
            <Container>
                {props.user != null && error === "" ? (
                    <Modal
                        open={open}
                        onClose={handleClose}
                        aria-labelledby="modal-modal-title"
                        aria-describedby="modal-modal-description"
                    >
                        <Box sx={modalStyle}>
                            <CreatePost user={props.user} handleClose={handleClose} />
                        </Box>
                    </Modal>
                ) : ""}

                {renderPosts()}

                <Fab style={fabStyle} onClick={handleOpen} color="primary" aria-label="add">
                    <AddIcon />
                </Fab>
            </Container>
        </div>
    );
}
export default Home;