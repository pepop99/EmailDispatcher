import React, { useState } from 'react';
import { toast } from 'react-toastify';
import axios from 'axios';
import './form.css';

const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT
const SaveNonProfits = () => {
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        SaveNonProfit();
    };

    const SaveNonProfit = () => {
        axios.get(`${API_ENDPOINT}/meta/save/np?e=${email}&n=${name}&a=${address}`)
            .then(response => {
                if (response.status === 200) {
                    toast.success("Non-Profit saved!");
                    setEmail('');
                    setName('');
                    setAddress('');
                }
            })
            .catch(error => {
                if (error.response) {
                    toast.error(error.response.data);
                }
            });
    };

    return (
        <div>
            <h2>Save Non-Profits</h2>
            <form onSubmit={handleSubmit}>
                <label>Email:</label>
                <input
                    type="email"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <label>Name:</label>
                <input
                    id="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                />
                <label>Address:</label>
                <input
                    id="address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    required
                />
                <button type="submit">Save Non-Profit</button>
            </form>
        </div>
    );
};

export default SaveNonProfits;