import './App.css';
import { useState, useEffect } from 'react';
import Chat from "./pages/Chat"
import Layout from "./pages/Layout"
import Home from "./pages/Home"
import Login from "./pages/Login"
import NoMatch from "./components/NoMatch"
import Register from './pages/Register';
import ChooseAvatar from './components/ChooseAvatar';
import Friends from './pages/Friends';
import Profile from './pages/Profile';
import { Routes, Route } from "react-router-dom";

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (localStorage.getItem("id") != null && user == null) {
      var loadUser = {
        id: localStorage.getItem("id"),
        firstname: localStorage.getItem("firstname"),
        lastname: localStorage.getItem("lastname"),
        username: localStorage.getItem("username"),
        imageURL: localStorage.getItem("imageURL")
      }
      console.log("User not alreay in memory")
      console.log(loadUser)
      setUser(loadUser);
    } else {
      console.log("User already in memory: ")
      console.log(user)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Layout user={user} setUser={setUser} />}>
          <Route index element={<Home user={user} />} />
          <Route path="login" element={<Login user={user} setUser={setUser} />} />
          <Route path="register" element={<Register />} />
          <Route path="chat" element={<Chat user={user} />} />
          <Route path="chooseAvatar" element={<ChooseAvatar user={user} />} />
          <Route path="friends" element={<Friends user={user} />} />
          <Route path="profile/:username" element={<Profile user={user} />} />
          <Route path="*" element={<NoMatch />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
