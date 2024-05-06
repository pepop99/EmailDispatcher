import React, { useState } from 'react';
import { toast } from 'react-toastify';
import DropDown from '../components/dropdown';
import axios from 'axios';
import Table from '../components/table';

const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT
const ViewDonationsPage = () => {
    const [selectedNonProfits, setSelectedNonProfits] = useState([]);
    const handleSetSelectedNonProfits = (data) => {
        setSelectedNonProfits(data);
        setEmails([]);
    }
    const [emails, setEmails] = useState([]);
    const handleSubmit = (e) => {
        e.preventDefault();
    };
    const onSubmit = () => {
        let validNonProfit = true;
        if (selectedNonProfits.length === 0) {
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
            url: `${API_ENDPOINT}/email/view`,
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
                    setEmails(response.data);
                    toast.success("Email list populated");
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
                <DropDown url={`${API_ENDPOINT}/meta/read/np`} label={"name"} isMulti={true} selectedOptions={selectedNonProfits} setSelectedOptions={handleSetSelectedNonProfits} />
                <br />
                <button type="submit" onClick={onSubmit}>View</button>
            </form>
            <Table data={emails} />
        </div>
    );
};

export default ViewDonationsPage;
