import React, { useState } from 'react';
import { toast } from 'react-toastify';
import DropDown from '../components/dropdown';
import axios from 'axios';
import Table from '../components/table';

const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT
const UploadCSVPage = () => {
    const [file, setFile] = useState(null);
    const handleSubmit = (e) => {
        e.preventDefault();
    };
    const onSubmit = () => {
        if (file == null) {
            toast.error("Please select a CSV");
            return;
        }
        const formData = new FormData();
        formData.append("csv", file);
        var config = {
            method: 'post',
            url: `${API_ENDPOINT}/csv/upload`,
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            data: formData
            // formData: formData
        };
        axios(config)
            .then(response => {
                if (response.status === 200) {
                    toast.success("CSV Upload successfully");
                }
            })
            .catch(error => {
                if (error.response) {
                    toast.error(error.response.data);
                }
            });
    }
    return (
        <div>
            <h2>Upload CSV</h2>
            <form onSubmit={handleSubmit}>
                <input type='file' onChange={(e) => setFile(e.target.files[0])}></input>
                <br />
                <button type="submit" onClick={onSubmit}>Upload</button>
            </form>
        </div>
    );
};

export default UploadCSVPage;
