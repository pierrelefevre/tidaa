import { Typography, CardHeader } from "@mui/material";
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import axios from 'axios';
import { useState, useEffect, useRef } from 'react';
import Alert from '@mui/material/Alert';
import { Card } from '@mui/material';
import Grid from '@mui/material/Grid';
import CardContent from '@mui/material/CardContent';
import CreateMessage from "../components/CreateMessage.js"
import Box from '@mui/system/Box';
import ListSubheader from '@mui/material/ListSubheader';
import userCache from '../userCache.js'
import API_URL from "../API_URL";
import GChart from "../components/GChart.js";


function Chat(props) {
    const [conversations, setConversations] = useState(null);
    const [error, setError] = useState('');

    const messagesEndRef = useRef(null)

    var loadConversations = () => {
        if (props.user == null)
            return;
        axios.get(API_URL.chat + '/chat/owner/' + props.user.id + encodeURI("?token=" + props.user.token))
            .then(function (response) {
                if (response.status === 200) {
                    setConversations(response.data);
                }
                else {
                    setError("Failed to load conversations");
                }
            })
            .catch(function (error) {
                setError(error.toString());
            });
    }

    useEffect(() => {
        loadConversations();
        const refreshPosts = setInterval(function () { loadConversations(); }, 750);
        return () => clearInterval(refreshPosts);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [props.user]);

    var compare = (a, b) => {
        if (a.id < b.id) {
            return -1;
        }
        if (a.id > b.id) {
            return 1;
        }
        return 0;
    }

    const renderMessages = (activeConversation) => {
        if (activeConversation.messages != null) {
            activeConversation.messages.sort(compare);
            return activeConversation.messages.map((message) => {
                var align = parseInt(message.senderId) === parseInt(props.user.id) ? "right" : "left"
                if (message.type === "IMAGE") {

                    return (
                        <Box key={"msgimg" + message.id}>
                            <Typography variant="body2" sx={{ textAlign: align }} >
                                {userCache.getById(message.senderId) != null ? userCache.getById(message.senderId).firstname + " " + userCache.getById(message.senderId).lastname : ""}
                            </Typography>
                            <Typography variant="body2" sx={{ textAlign: align }} >
                                {new Date(message.timestamp).toLocaleString("sv-SE", { year: "numeric", month: "numeric", day: "numeric", hour: "numeric", minute: "numeric" })}
                            </Typography>
                            <Typography variant="body1" sx={{ textAlign: align, mb: 2 }}>
                                <img src={API_URL.image + message.content} style={{ maxWidth: "100%" }} alt="message" />
                            </Typography>
                        </Box>
                    )
                }

                if (message.type === "CHART") {

                    return (
                        <Box key={"msgimg" + message.id}>
                            <Typography variant="body2" sx={{ textAlign: align }} >
                                {userCache.getById(message.senderId) != null ? userCache.getById(message.senderId).firstname + " " + userCache.getById(message.senderId).lastname : ""}
                            </Typography>
                            <Typography variant="body2" sx={{ textAlign: align }} >
                                {new Date(message.timestamp).toLocaleString("sv-SE", { year: "numeric", month: "numeric", day: "numeric", hour: "numeric", minute: "numeric" })}
                            </Typography>
                            <Box variant="body1" sx={{ textAlign: align, mb: 2 }}>
                                <GChart id={message.content} />
                            </Box>
                        </Box>
                    )

                }

                return (
                    <Box key={"msg" + message.id}>
                        <Typography variant="body2" sx={{ textAlign: align }} >
                            {userCache.getById(message.senderId) != null ? userCache.getById(message.senderId).firstname + " " + userCache.getById(message.senderId).lastname : ""}
                        </Typography>
                        <Typography variant="body2" sx={{ textAlign: align }} >
                            {new Date(message.timestamp).toLocaleString("sv-SE", { year: "numeric", month: "numeric", day: "numeric", hour: "numeric", minute: "numeric" })}
                        </Typography>
                        <Typography variant="body1" sx={{ textAlign: align, mb: 2 }}>
                            {message.content}
                        </Typography>
                    </Box>
                )

            });
        }
        else return null
    }

    return (
        <>
            {error ? <Alert severity="error">{error}</Alert> : null}
            <Grid container spacing={5} sx={{ width: "100vw", boxSizing: "border-box", position: "absolute", top: "150px", left: 0, overflowX: "hidden" }} >
                {(conversations != null) ?
                    conversations.map((conversation) => {
                        return (
                            <Grid container direction="row">
                                <Grid item xs={1}>
                                </Grid>
                                <Grid item xs={5}>
                                    <Card sx={{ textAlign: "left", mb: 2 }}>
                                        <CardHeader
                                            title={conversation.chatName}
                                        />
                                        <CardContent sx={{ overflowY: "scroll", maxHeight: "60vh" }}>
                                            {conversation.messages != null ? renderMessages(conversation) : ""}
                                            <div ref={messagesEndRef} />
                                        </CardContent>

                                        {conversation.messages != null ? <CreateMessage user={props.user} receiver={conversation} /> : ""}
                                    </Card>
                                </Grid>
                                <Grid item xs={5}>
                                    <iframe src={"https://pierrelefevre.github.io/metaberse_whiteboard_frontend/index.html?id=" + conversation.chatId} style={{ width: "100%", height: "500px" }}></iframe>
                                </Grid>
                                <Grid item xs={1}>
                                </Grid>
                            </Grid>
                        )
                    })
                    : null}
            </Grid>
        </>
    )
}

export default Chat;