import React, { useState } from 'react';
import { toast } from 'react-toastify';
import axios from 'axios';
import Table from 'rc-table';
import '../components/table.css';

const API_ENDPOINT = process.env.REACT_APP_API_ENDPOINT
const ViewDonationsPage = () => {
    const [headers, setHeaders] = useState([]);
    const [tableData, setTableData] = useState([]);
    const handleSubmit = (e) => {
        e.preventDefault();
    };
    const onSubmit = () => {
        var config = {
            method: 'get',
            url: `${API_ENDPOINT}/csv/fetch`,
            headers: {
                'Content-Type': 'application/json'
            },
        };
        axios(config)
            .then(response => {
                if (response.status === 200) {
                    parseAndSetHeaders(response.data.headers);
                    setTableData(response.data.data);
                }
            })
            .catch(error => {
                if (error.response) {
                    toast.error(error.response.data);
                }
            });
    }
    const parseAndSetHeaders = (headers) => {
        var tempHeaders = headers.replace(/[\[\]']+/g, '');
        var headerArray = tempHeaders.split(",");
        var tableHeaderArray = [];
        headerArray.forEach((element) => {
            var trimmedElement = element.trim();
            var obj = {
                title: trimmedElement,
                dataIndex: trimmedElement,
                key: trimmedElement,
            }
            tableHeaderArray.push(obj);
        });
        setHeaders(tableHeaderArray);

    }

    return (
        <div>
            <h2>Donations</h2>
            <form onSubmit={handleSubmit}>
                <button type="submit" onClick={onSubmit}>View Donations</button>
            </form>
            <div className='tableContainerDiv'>
                {headers.length !== 0 ? <Table columns={headers} data={tableData} /> : null}
            </div>
        </div>
    );
};

export default ViewDonationsPage;
