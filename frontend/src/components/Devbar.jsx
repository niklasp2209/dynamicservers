import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Link, useNavigate } from "react-router-dom";

const Devbar = () => {
    const { user, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <nav className="bg-gray-800 text-white p-4 flex justify-between">
            <Link to="/dashboard" className="text-xl font-bold">DynamicServers</Link>
            <div>
                {user ? (
                    <>
                        <span className="mr-4">Hallo, {user.username}!</span>
                        <button onClick={handleLogout} className="bg-red-500 px-4 py-2 rounded">
                            Logout
                        </button>
                    </>
                ) : (
                    <Link to="/login" className="bg-blue-500 px-4 py-2 rounded">Login</Link>
                )}
            </div>
        </nav>
    );
};

export default Devbar;