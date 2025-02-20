import { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await login(username, password);
            navigate("/dashboard");
        } catch (error) {
            alert("Login fehlgeschlagen!");
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-lg">
                <h2 className="text-xl font-bold mb-4">Login</h2>
                <input
                    type="text"
                    placeholder="Username"
                    className="w-full p-2 border mb-2"
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Passwort"
                    className="w-full p-2 border mb-2"
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button
                    type="submit"
                    className="bg-blue-500 text-white w-full py-2 rounded"
                >
                    Login
                </button>
                <p className="mt-4 text-center text-gray-400">
                    Noch kein Konto? <button onClick={() => navigate("/register")} className="text-blue-500">Jetzt registrieren</button>
                </p>
            </form>
        </div>
    );
};

export default LoginPage;
