import axios from "axios";


var userCache = {
    url: "http://localhost:8081/users/",

    getByUsername: function (username) {
        var users = JSON.parse(localStorage.getItem("users"));
        if (users == null) {
            users = [];
        }

        for (var i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].username === username) {
                return users[i];
            }
        }

        axios.get(this.url + username)
            .then(function (response) {
                if (response.status === 200) {
                    var user = response.data;
                    users[user.id] = user;
                    localStorage.setItem("users", JSON.stringify(users));
                    return user
                }
            })
            .catch(function (error) {
                console.error("UserCache error: " + JSON.stringify(error));
            });
    },

    getById: function (id) {

        var users = JSON.parse(localStorage.getItem("users"));
        if (users == null) {
            users = [];
        }

        if (id === null) {
            return null;
        }

        if (users[id] != null)
            return users[id]

        // Actually get it and save it
        console.log("Trying to get user with id " + id);
        axios.get(this.url + id)
            .then(function (response) {
                if (response.status === 200) {
                    var user = response.data;
                    users[user.id] = user;
                    localStorage.setItem("users", JSON.stringify(users));
                    return user
                }
                return null
            })
            .catch(function (error) {
                console.error("UserCache error: " + JSON.stringify(error));
                return null;
            });
    },
    invalidate: function () {
        localStorage.removeItem("users");
    }

}


export default userCache