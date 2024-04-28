import React, { useState } from 'react';
import { toast } from 'react-toastify';
import axios from 'axios';
import './form.css';

const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT
const SaveFoundations = () => {
    const [email, setEmail] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        saveFoundation();
    };

    const saveFoundation = () => {
        axios.get(`${API_ENDPOINT}/meta/save/fd?e=${email}`)
            .then(response => {
                if (response.status === 200) {
                    toast.success("Foundation saved!");
                    setEmail('');
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
            <h2>Save Foundations</h2>
            <form onSubmit={handleSubmit}>
                <label>Email:</label>
                <input
                    type="email"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <button type="submit">Save Foundation</button>
            </form>
        </div>
    );
};

export default SaveFoundations;
