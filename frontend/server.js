const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const bodyParser = require('body-parser');

mongoose.connect('mongodb://localhost:27017/dynamicserver', {
    useNewUrlParser: true,
    useUnifiedTopology: true
})
    .then(() => console.log("Datenbank verbunden"))
    .catch(err => console.log(err));

const app = express();
app.use(bodyParser.json());

const userSchema = new mongoose.Schema({
    username: String,
    email: String,
    password: String
});

const User = mongoose.model('User', userSchema);

app.post('/auth/register', async (req, res) => {
    const {username, email, password} = req.body;

    const userExists = await User.findOne({email});
    if (userExists) return res.status(400).send("Benutzer existiert bereits.");

    const hashedPassword = await bcrypt.hash(password, 10);

    const user = new User({username, email, password: hashedPassword});
    await user.save();

    res.status(201).send("Registrierung erfolgreich!");
});

app.listen(5000, () => {
    console.log('Server läuft auf http://localhost:5000');
});
