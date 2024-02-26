import React, { useState } from 'react';

const CopyLinkButton = () => {
    const [copySuccess, setCopySuccess] = useState(false);

    const handleCopy = () => {
        const url = window.location.href;
        navigator.clipboard.writeText(url).then(() => {
            setCopySuccess(true);
        });
    };

    return (
        <div>
            <button className="hover:bg-gray-300 " onClick={handleCopy}>Copy Link</button>
        </div>
    );
};

export default CopyLinkButton;
