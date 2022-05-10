import { Card, CardHeader, Avatar, CardContent, Typography, Chip } from '@mui/material';
import { Link } from "react-router-dom";
import { red } from '@mui/material/colors';
import GChart from './GChart';

const Post = (props) => {
    var dt = new Date(props.timestamp).toLocaleString("sv-SE", { year: "numeric", month: "numeric", day: "numeric", hour: "numeric", minute: "numeric" })

    return (
        <Card sx={{ textAlign: "left", mb: 2 }}>
            <CardHeader
                avatar={
                    props.image == null ?
                        <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                            {props.username.charAt(0)}
                        </Avatar>
                        : <Avatar src={"https://metaberse-imageapi.herokuapp.com" + props.image} sx={{ bgcolor: red[500] }} aria-label="recipe" />
                }
                title={props.title}
                subheader={dt}
                action={<Chip label={"@" + props.username} component={Link} sx={{ textDecoration: "none", cursor: "pointer" }} to={"/profile/" + props.username} />}
            />
            {props.type === "TEXT" ?

                <CardContent>
                    <Typography variant="body2" color="text.secondary">
                        {props.content}
                    </Typography>
                </CardContent>

                :
                <GChart id={props.content} />
            }

        </Card>
    )
}

export default Post;