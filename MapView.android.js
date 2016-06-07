import React, { Component } from 'react';

import {
    PropTypes,
    View,
    requireNativeComponent,
} from 'react-native';

class AMapCustomView extends Component {
    constructor(props) {
        super(props)
        this._onRegionChange = this._onRegionChange.bind(this)
    }

    _onRegionChange(event: Event) {
        if (!this.props.onRegionChange) {
            return
        }
        this.props.onRegionChange(event.nativeEvent)
    }

    render() {
        console.log(this.props)
        return <RCTAMap {...this.props} onRegionChange={this._onRegionChange}/>
    }
}

AMapCustomView.propTypes = {
    ...View.propTypes,
    mode: PropTypes.number,
	showUserLocation: PropTypes.bool,
	annotations: PropTypes.array,
    onRegionChange: PropTypes.func
}

var RCTAMap = requireNativeComponent('RCTAMap', AMapCustomView)

module.exports = AMapCustomView;
