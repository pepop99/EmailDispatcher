// Table.js
import React from 'react';
import './table.css'; // Import CSS file for styling

const Table = ({ data }) => {
    return (
        <div className="table-container">
            <table className="table">
                <tbody>
                    {data.map((row, index) => (
                        <tr key={index}>
                            <td>{row}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Table;
