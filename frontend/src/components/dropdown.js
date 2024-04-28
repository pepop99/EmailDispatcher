import Select from 'react-select';
import axios from 'axios';
import React, { useState, useEffect } from 'react';
import './select.css';

function DropDown({ url, label, isMulti, selectedOptions, setSelectedOptions }) {
    const [dropdownOpened, setDropdownOpened] = useState(false);
    const [dropdownOptions, setDropdownOptions] = useState([]);
    // const [selectedOptions, setSelectedOptions] = useState([]);
    const refreshDropdown = () => {
        axios.get(url)
            .then(response => {
                const options = response.data.map(item => ({
                    value: item,
                    label: item[label]
                }));
                let filteredOptions = []
                if (selectedOptions instanceof Array) {
                    filteredOptions = options.filter(item => !selectedOptions.find(e => e.value.email === item.value.email));
                } else {
                    filteredOptions = options.filter(item => selectedOptions.value.email !== item.value.email);
                }
                setDropdownOptions(filteredOptions);
            })
            .catch(error => {
                console.error('Error fetching user list:', error);
            });
    }
    useEffect(() => {
        if (dropdownOpened) {
            refreshDropdown();
        } else if (!dropdownOpened) {
            setDropdownOpened([]);
        }
    }, [dropdownOpened, selectedOptions]);
    // also binded refreshDropdown to page load
    useEffect(() => {
        refreshDropdown();
    }, []);
    const handleChange = (selectedOptions) => {
        setSelectedOptions(selectedOptions);
    };

    return (
        <Select
            options={dropdownOptions}
            value={selectedOptions}
            onChange={handleChange}
            isMulti={isMulti}
            onMenuOpen={() => setDropdownOpened(true)}
            onMenuClose={() => setDropdownOpened(false)}
            className='Select'
            classNamePrefix='Select'
        />
    );
}

export default DropDown;
