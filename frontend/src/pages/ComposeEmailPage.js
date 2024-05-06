import React, { useState } from 'react';
import { toast } from 'react-toastify';
import DropDown from '../components/dropdown';
import axios from 'axios';

const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT

const ComposeEmailPage = () => {
    const handleSubmit = (e) => {
        e.preventDefault();
    };
    const [selectedFoundation, setSelectedFoundation] = useState([]);
    const [selectedNonProfits, setSelectedNonProfits] = useState([]);
    const [carbonCopy, setCarbonCopy] = useState("");
    const [blackCarbonCopy, setBlackCarbonCopy] = useState("");

    const onSubmit = () => {
        let validFoundation = true;
        if (selectedFoundation instanceof Array && selectedFoundation.length === 0) {
            toast.error("Please select a Foundation");
            validFoundation = false;
        }
        let validNonProfit = true;
        if (selectedNonProfits.length === 0) {
            toast.error("Please select atleast 1 Non-Profit");
            validNonProfit = false;
        }
        if (validFoundation && validNonProfit) {
            sendEmail();
        }
    }
    const sendEmail = () => {
        var config = {
            method: 'post',
            url: `${API_ENDPOINT}/email/send`,
            headers: {
                'Content-Type': 'text/plain'
            },
            data: {
                'fd': selectedFoundation,
                'np': selectedNonProfits,
                'cc': carbonCopy,
                'bcc': blackCarbonCopy
            }
        };
        axios(config)
            .then(response => {
                if (response.status === 200) {
                    toast.success("Email sent");
                    setSelectedFoundation([]);
                    setSelectedNonProfits([]);
                    setCarbonCopy("");
                    setBlackCarbonCopy("");
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
            <h2>Compose Email Page</h2>
            <form onSubmit={handleSubmit}>
                <label>Foundation:</label>
                <DropDown url={`${API_ENDPOINT}/meta/read/fd`} label={"email"} isMulti={false} selectedOptions={selectedFoundation} setSelectedOptions={setSelectedFoundation} />
                <br />
                <label>Non-Profits:</label>
                <DropDown url={`${API_ENDPOINT}/meta/read/np`} label={"name"} isMulti={true} selectedOptions={selectedNonProfits} setSelectedOptions={setSelectedNonProfits} />
                <br />
                <label>cc:</label>
                <input
                    type="email"
                    id="cc"
                    value={carbonCopy}
                    onChange={(e) => setCarbonCopy(e.target.value)}
                />
                <br />
                <label>bcc:</label>
                <input
                    type="email"
                    id="bcc"
                    value={blackCarbonCopy}
                    onChange={(e) => setBlackCarbonCopy(e.target.value)}
                />
                <br />
                <button type="submit" onClick={onSubmit}>Send Email</button>
            </form>
        </div>
    );
};

export default ComposeEmailPage;