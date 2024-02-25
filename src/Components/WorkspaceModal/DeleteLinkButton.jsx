import React, { useState } from 'react';

const DeleteLinkButton = () => {
    const [copiedLink, setCopiedLink] = useState('');
    const [deleteSuccess, setDeleteSuccess] = useState(false);

    const handleReadClipboard = () => {
        navigator.clipboard.readText()
            .then(text => {
                setCopiedLink(text);
                window.location.href = '';
                setDeleteSuccess(true);
            })
            .catch(error => console.error('Failed to read clipboard contents: ', error));
    };

    return (
        <div>
            <button onClick={handleReadClipboard}>Delete Link</button>

        </div>
    );
};

export default DeleteLinkButton;
