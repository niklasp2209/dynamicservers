const ServerCard = ({name, status, players, onStart, onStop}) => {
    return (
        <div className="bg-white shadow-lg rounded-lg p-4">
            <h2 className="text-xl font-bold">{name}</h2>
            <p>Status: <span
                className={status === "Online" ? "text-green-500" : "text-red-500"}>{status}</span>
            </p>
            <p>Spieler: {players}</p>
            <div className="mt-4">
                {status === "Offline" ? (
                    <button onClick={onStart}
                            className="bg-green-500 text-white px-4 py-2 rounded">Start</button>
                ) : (
                    <button onClick={onStop}
                            className="bg-red-500 text-white px-4 py-2 rounded">Stop</button>
                )}
            </div>
        </div>
    );
};

export default ServerCard;
