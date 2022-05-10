import axios from 'axios';
import { useState, useEffect } from 'react';
import Alert from '@mui/material/Alert';
import Grid from '@mui/material/Grid';
import Box from '@mui/system/Box';
import { TextField } from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import { Search as SearchIcon } from '@mui/icons-material';
import { Card, CardHeader, Avatar, Typography, Chip } from '@mui/material';
import { red } from '@mui/material/colors';
import Paper from '@mui/material/Paper';
import { useNavigate } from "react-router-dom";
import API_URL from "../API_URL";


function Friends(props) {
    const [searchString, setSearchString] = useState("");
    const [loading, setLoading] = useState(false);
    const [friendError, setFriendError] = useState('');
    const [searchError, setSearchError] = useState('');
    const [result, setResult] = useState([]);
    const [friends, setFriends] = useState([]);
    const navigate = useNavigate();

    var search = (e) => {
        if (e)
            e.preventDefault();
        setLoading(true);
        axios.get(API_URL.user + '/users/search?query=' + searchString)
            .then(function (response) {
                setLoading(false);
                if (response.status === 200) {
                    console.log(response.data);
                    setResult(response.data);
                    setSearchError("");
                }
                else {
                    setSearchError("Failed to search " + searchString);
                }
            })
            .catch(function (error) {
                setLoading(false);
                setSearchError(error.toString());
            });
    }

    var message = (user) => {
        axios.post(API_URL.chat + '/chat', {
            chatName: props.user.username + " <->   " + user.username,
            users: [props.user.id, user.id]
        })
            .then(function () {
                navigate('/chat');
            })
            .catch(function (error) {
                setLoading(false);
                setFriendError(error.toString());
            });
    }

    var getFriends = () => {
        if (!props.user) { setFriendError("You must be logged in to view friends"); return; }
        setLoading(true);
        console.log(props.user.id);
        axios.get(API_URL.user + '/users/' + props.user.id + "/friends")
            .then(function (response) {
                setLoading(false);
                if (response.status === 200) {
                    console.log(response.data);
                    setFriends(response.data);
                    setFriendError("");
                }
                else {
                    setFriendError("Failed to load friends from " + props.user.id);
                }
            })
            .catch(function (error) {
                setLoading(false);
                setFriendError(error.toString());
            });
    }

    var addFriend = (friend) => {
        axios.post(API_URL.user + '/users/' + props.user.id + "/friends/" + friend.id)
            .then(function () {
                getFriends();
                setSearchError("");
            })
            .catch(function (error) {
                setSearchError(error.toString());
            });
    }

    var removeFriend = (friend) => {
        axios.delete(API_URL.user + '/users/' + props.user.id + "/friends/" + friend.id)
            .then(function () {
                getFriends();
                setFriendError("");
            })
            .catch(function (error) {
                setFriendError(error.toString());
            });
    }

    useEffect(() => {
        getFriends();
        // const refreshFriends = setInterval(function () { getFriends(); }, 1000);
        // return () => clearInterval(refreshFriends);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return (
        <>
            <Grid container spacing={2}>
                <Grid item xs={6}>
                    <h1>Search</h1>
                    {searchError ? <Alert severity="error" sx={{ mb: 2 }}>{searchError}</Alert> : null}

                    <Paper>
                        <Box
                            component="form" onSubmit={search}
                            sx={{
                                display: 'flex',
                                flexDirection: 'row',
                                alignItems: 'center',
                                mb: 2
                            }}
                        >
                            <TextField
                                sx={{ flexGrow: 1, m: 1 }}
                                id="search"
                                label="Search"
                                name="search"
                                value={searchString}
                                onInput={e => setSearchString(e.target.value)}
                            />

                            <LoadingButton
                                loading={loading}
                                variant="contained"
                                type="submit"
                                size="large"
                                sx={{ m: 1 }}
                            >
                                <SearchIcon />
                            </LoadingButton>
                        </Box >
                    </Paper>

                    {result.map((friend) => {

                        if (friend.id === props.user.id) {
                            return (null)
                        }

                        return (
                            <Card sx={{ textAlign: "left", mb: 2 }} key={friend.id}>
                                <CardHeader
                                    avatar={
                                        friend.imageURL == null ?
                                            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                                                {friend.username.charAt(0)}
                                            </Avatar>
                                            : <Avatar src={"http://localhost:8084" + friend.imageURL} sx={{ bgcolor: red[500] }} aria-label="recipe" />
                                    }
                                    title={friend.firstname + " " + friend.lastname}
                                    subheader={"@" + friend.username}
                                    action={
                                        <>
                                            <Chip label={"Message"} sx={{ textDecoration: "none", cursor: "pointer", ml: 1 }} onClick={() => message(friend)} />
                                            <Chip label={"Add friend"} sx={{ textDecoration: "none", cursor: "pointer", ml: 1 }} onClick={() => addFriend(friend)} />
                                        </>
                                    }
                                />
                            </Card>
                        )
                    }
                    )
                    }
                </Grid>

                <Grid item xs={6}>
                    <h1>Your friends</h1>
                    {friendError ? <Alert severity="error" sx={{ mb: 2 }}>{friendError}</Alert> : null}

                    {friends.map((friend) =>
                        <Card sx={{ textAlign: "left", mb: 2 }} key={friend.id}>
                            <CardHeader
                                avatar={
                                    friend.imageURL == null ?
                                        <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                                            {friend.username.charAt(0)}
                                        </Avatar>
                                        : <Avatar src={"http://localhost:8084" + friend.imageURL} sx={{ bgcolor: red[500] }} aria-label="recipe" />
                                }
                                title={friend.firstname + " " + friend.lastname}
                                subheader={"@" + friend.username}
                                action={
                                    <>
                                        <Chip label={"Message"} sx={{ textDecoration: "none", cursor: "pointer", ml: 1 }} onClick={() => message(friend)} />
                                        <Chip label={"Remove friend"} sx={{ textDecoration: "none", cursor: "pointer", ml: 1 }} onClick={() => removeFriend(friend)} />
                                    </>
                                }
                            />
                        </Card>
                    )}
                    {friends.length === 0 ? <Typography variant="body1">You have no friends :(</Typography> : null}
                </Grid>
            </Grid>
        </>
    )
}

export default Friends;