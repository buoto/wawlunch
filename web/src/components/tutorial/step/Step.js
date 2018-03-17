import React, { PureComponent } from 'react';

class Step extends PureComponent {
    render() {
        const { children } = this.props;

        return (
            <div className="step">
                {children}
            </div>
        );
    }
}

export default Step;
