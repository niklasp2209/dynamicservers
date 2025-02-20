import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../api/authApi";

const RegisterPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            await axios.post("/auth/register", { username, password });
            alert("Registrierung erfolgreich! Bitte einloggen.");
            navigate("/login");
        } catch (error) {
            alert("Registrierung fehlgeschlagen!");
        }
    };

    return (
        <div className="flex items-center justify-center h-screen bg-gray-100">
            <form onSubmit={handleRegister} className="bg-white p-6 rounded-lg shadow-lg">
                <h2 className="text-xl font-bold mb-4">Registrieren</h2>
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
                    className="bg-green-500 text-white w-full py-2 rounded"
                >
                    Registrieren
                </button>
                <p className="mt-4 text-center text-gray-400">
                    Schon ein Konto? <button onClick={() => navigate("/login")} className="text-blue-500">Login</button>
                </p>
            </form>
        </div>
    );
};

export default RegisterPage;
