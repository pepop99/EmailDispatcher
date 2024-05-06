// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import SaveFoundations from './pages/SaveFoundations';
import SaveNonProfits from './pages/SaveNonProfits';
import ComposeEmailPage from './pages/ComposeEmailPage';
import ViewSentEmailsPage from './pages/ViewSentEmailsPage';
import ViewDonationsPage from './pages/ViewDonationsPage';
import UploadCSVPage from './pages/UploadCSVPage';
import './App.css';

const App = () => {
  const saveFoundationsPath = "/save-foundations";
  const saveNonProfitsPath = "/save-nonprofits";
  const composeEmailPath = "/composeEmail";
  const viewSentEmailsPath = "/viewSentEmails";
  const uploadCSVPage = "/uploadCSVPage"
  const viewDonationsPage = "/viewDonationsPage";
  return (
    <div>
      <Router basename='EmailDispatcher'>
        <div>
          <nav>
            <ul>
              <li>
                <Link to={saveNonProfitsPath}>Save Non-Profit</Link>
              </li>
              <li>
                <Link to={saveFoundationsPath}>Save Foundation</Link>
              </li>
              <li>
                <Link to={composeEmailPath}>Compose Email</Link>
              </li>
              <li>
                <Link to={viewSentEmailsPath}>View Sent Emails</Link>
              </li>
              <li>
                <Link to={uploadCSVPage}>Upload CSV</Link>
              </li>
              <li>
                <Link to={viewDonationsPage}>View Donations</Link>
              </li>
            </ul>
          </nav>

          <Routes>
            <Route path="/" element={<SaveNonProfits />} />
            <Route path={saveFoundationsPath} element={<SaveFoundations />} />
            <Route path={saveNonProfitsPath} element={<SaveNonProfits />} />
            <Route path={composeEmailPath} element={<ComposeEmailPage />} />
            <Route path={viewSentEmailsPath} element={<ViewSentEmailsPage />} />
            <Route path={uploadCSVPage} element={<UploadCSVPage />} />
            <Route path={viewDonationsPage} element={<ViewDonationsPage />} />
          </Routes>
        </div>
      </Router>
      <ToastContainer />
    </div>
  );
};

export default App;
