import {useContext} from "react";
import {AuthContext} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";

const Dashboard = () => {
    const {user, logout} = useContext(AuthContext);
    const navigate = useNavigate();

    if (!user) {
        navigate("/login");
        return null;
    }

    return (
        <div className="p-6">
            <h1 className="text-2xl font-bold">Willkommen, {user.username}!</h1>
            <button onClick={logout}
                    className="bg-red-500 text-white px-4 py-2 mt-4 rounded">
                Logout
            </button>
        </div>
    );
};

export default Dashboard;
