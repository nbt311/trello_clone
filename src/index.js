import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import {BrowserRouter} from "react-router-dom";
import {ChakraProvider} from "@chakra-ui/react";
import {UserProvider} from "./Context/UserContext";
import {BoardProvider} from "./Context/BoardContext";
import {WorkspaceProvider} from "./Context/WorkspaceContext";
import {NotificationProvider} from "./Context/NotificationContext";


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <ChakraProvider>
            <UserProvider>
                <NotificationProvider>
                    <WorkspaceProvider>
                        <BoardProvider>
                            <App />
                        </BoardProvider>
                    </WorkspaceProvider>
                </NotificationProvider>
            </UserProvider>
        </ChakraProvider>
    </BrowserRouter>
);

