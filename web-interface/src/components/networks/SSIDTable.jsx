import React from 'react';
import Reflux from 'reflux';
import SSIDRow from "./SSIDRow";

class SSIDTableRow extends Reflux.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const self = this;

        return (
            <tr style={{"display": this.props.display ? "" : "none"}} >
                <td colSpan="6">
                    <table className="table table-sm table-hover table-striped">
                        <thead>
                            <tr>
                                <th>SSID</th>
                                <th>Channel</th>
                                <th>Total Frames</th>
                                <th>Quality (min)</th>
                                <th>Quality (max)</th>
                                <th>Fingerprint</th>
                            </tr>
                        </thead>
                        <tbody>
                            {Object.keys(this.props.ssid.channels).map(function (key,i) {
                                return <SSIDRow key={i} ssid={self.props.ssid.name} channelNumber={key} channel={self.props.ssid.channels[key]} />;
                            })}
                        </tbody>
                    </table>
                </td>
            </tr>
        )
    }

}

export default SSIDTableRow;