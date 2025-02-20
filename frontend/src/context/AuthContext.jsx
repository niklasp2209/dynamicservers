import {createContext, useState, useEffect} from "react";
import axios from "../api/authApi.js";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const loggedUser = localStorage.getItem("user");
        if (loggedUser) setUser(JSON.parse(loggedUser));
    }, []);

    const login = async (username, password) => {
        const res = await axios.post("/auth/login", {username, password});
        localStorage.setItem("user", JSON.stringify({username}));
        setUser({username});
    };

    const logout = () => {
        localStorage.removeItem("user");
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{user, login, logout}}>
            {children}
        </AuthContext.Provider>
    );
};