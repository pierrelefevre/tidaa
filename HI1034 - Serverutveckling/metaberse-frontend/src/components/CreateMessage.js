import { useState } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Alert from '@mui/material/Alert';
import LoadingButton from '@mui/lab/LoadingButton';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import FormControl from '@mui/material/FormControl';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Modal from '@mui/material/Modal';
import CreateChart from "./CreateChart";
import API_URL from "../API_URL";

const CreateMessage = (props) => {
    const [content, setContent] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [file, setFile] = useState(null);
    const [fileKey, setFileKey] = useState(0);
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    var handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);

        if (file != null) {
            console.log("I'm a file man")
            sendFile(e);
        }
        if (content.length !== 0) {
            console.log("I'm a message man")
            axios.post(API_URL.chat + '/messages', {
                chatId: props.receiver.chatId,
                senderId: props.user.id,
                type: "TEXT",
                content: content,
                timestamp: new Date().toISOString(),
                token: props.user.token
            })
                .then(function (response) {
                    console.log(response);
                    setLoading(false);
                    if (response.status === 201) {
                        console.log("Successfully sent message!");

                        setError("");
                        setContent("");
                    }
                    else {
                        setError("Failed to send message");
                    }
                })
                .catch(function (error) {
                    setLoading(false);
                    console.log(error);
                    setError(error.toString());
                });
        }
    }

    var sendFile = (e) => {
        console.log(file.name);
        const formData = new FormData();
        formData.append('file', file);
        formData.append("userID", props.user.id);
        formData.append("type", "MESSAGE");
        formData.append("chatID", props.receiver.chatId);
        formData.append("token", props.user.token);

        axios.post(
            API_URL.image + '/images',
            formData)
            .then(function (response) {
                console.log(response);
                setFileKey(response.data);
                setFile(null);

                if (response.status === 201) {
                    setError("");
                    setLoading(false);
                }
                else {
                    setError("Failed to upload file. Try again");
                    setLoading(false);
                }
            })
            .catch(function (error) {
                setError(error.toString());
                setLoading(false);
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
        <Paper>
            <Box
                component="form" onSubmit={handleSubmit}
                sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    alignItems: 'center',
                    mt: 1
                }}
            >
                <TextField
                    sx={{ flexGrow: 1, m: 1 }}
                    id="content"
                    label="Compose message"
                    name="content"
                    value={content}
                    onInput={e => setContent(e.target.value)}
                />


                <LoadingButton
                    loading={loading}
                    variant="contained"
                    type="submit"
                    size="large"
                    sx={{ m: 1 }}
                >
                    Send
                </LoadingButton>
            </Box>

            <Box
                component="form" onSubmit={handleSubmit}
                sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    alignItems: 'center',
                    mt: 1
                }}
            >
                <Fab onClick={handleOpen} color="primary" aria-label="add">
                    <AddIcon />
                </Fab>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={modalStyle}>
                        <CreateChart user={props.user} conversation={props.receiver} handleClose={handleClose} />
                    </Box>
                </Modal>

                <FormControl sx={{ mb: 1, mx: 1 }}>
                    <input id="profilePic" type="file" key={fileKey} onChange={e => onFileChange(e)} />
                </FormControl>
            </Box >

            {renderError()}
        </Paper >
    )

}

export default CreateMessage;