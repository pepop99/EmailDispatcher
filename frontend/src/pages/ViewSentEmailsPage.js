import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import DropDown from '../components/dropdown';
import axios from 'axios';


const ViewSentEmailsPage = () => {
    const [selectedNonProfits, setSelectedNonProfits] = useState([]);
    const [emails, setEmails] = useState([]);
    const handleSubmit = (e) => {
        e.preventDefault();
    };
    const onSubmit = () => {
        let validNonProfit = true;
        if (selectedNonProfits.length == 0) {
            toast.error("Please select atleast 1 Non-Profit");
            validNonProfit = false;
        }
        if (validNonProfit) {
            setEmails([]);
            getEmails();
        }
    }
    const getEmails = () => {
        var config = {
            method: 'post',
            url: 'http://localhost:8080/email/view',
            headers: {
                'Content-Type': 'text/plain'
            },
            data: {
                'np': selectedNonProfits
            }
        };
        axios(config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response.data);
                    console.log(response.data instanceof Array);
                    // setEmails(response.data);
                    // setSelectedNonProfits([]);
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
            <h2>View Sent Emails</h2>
            <form onSubmit={handleSubmit}>
                <label>Non-Profits:</label>
                <DropDown url={"http://localhost:8080/meta/read/np"} label={"name"} isMulti={true} selectedOptions={selectedNonProfits} setSelectedOptions={setSelectedNonProfits} />
                <br />
                <button type="submit" onClick={onSubmit}>View</button>
                <ul>
                    {/* Use map to render each item in the array */}
                    {emails.map((item, index) => (
                        <li key={index}>{item}</li>
                    ))}
                </ul>
            </form>
        </div>
    );
};

export default ViewSentEmailsPage;
