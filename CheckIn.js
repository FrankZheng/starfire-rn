import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Image,
  Navigator,
  TouchableWithoutFeedback
} from 'react-native';

import NavigationBar from './NavigationBar';
import AMapCustomView from './MapView';
import ActionSheet from 'react-native-action-sheet';


var Location = require('./Location');
var log = require('./Log');

const TAG = "CheckIn";

const buttons = [
  'One',
  'Two',
  'Three',
];


class CheckIn extends Component {
  constructor(props) {
    super(props);
    this._onRegionChange = this._onRegionChange.bind(this);
    this._onPress = this._onPress.bind(this);
    this._onCancel = this._onCancel.bind(this);
    this._onSelectSheet = this._onSelectSheet.bind(this);
    this.state = {
      address: '',
      showActionSheet: false,
      assignType: null,
    }
  }

  componentDidMount() {
    log.d(TAG, "componentDidMount");
    Location.getPosition((loc) => {
      log.d(TAG, loc.latitude + ", " + loc.longitude);
    }, (error) => {
      log.e(TAG, error);
    });
  }

  _onRegionChange(event) {
    log.d(TAG, event.address);
    this.setState({
      address: "当前定位: " + event.address,
    });
  }

  _onPress() {
    log.d(TAG, "onPress");
    this.setState({
      showActionSheet: true,
    });

  }

  _onCancel() {
    log.d(TAG, "onCancel");
    this.setState({
      showActionSheet: false,
    });
  }

  _onSelectSheet(index) {
    log.d(TAG, "select sheet, " + index);
    this.setState({
      assignType: buttons[index],
      showActionSheet: false,
    })
  }

  render() {
    return (
      <View style={styles.main}>
        <NavigationBar title="签到" navigator={this.props.navigator}/>
        <View style={styles.content}>
          <AMapCustomView showUserLocation={true}
          style={styles.mapView}
          onRegionChange={this._onRegionChange}/>
          <View style={styles.addressPanel}>
            <Text style={styles.address}>
              {this.state.address}
            </Text>
          </View>
          <TouchableWithoutFeedback onPress={this._onPress}>
            <View style={styles.rowItem}>
              <Text style={styles.rowItemTitle}>
                签到类型
              </Text>
              <Text style={styles.rowItemForward}>
                {this.state.assignType == null ? '>' : this.state.assignType}
              </Text>
            </View>
          </TouchableWithoutFeedback>
          <ActionSheet
            modalVisible={this.state.showActionSheet}
            onCancel={this._onCancel}>
            {
              buttons.map((title, index) => {
                return <ActionSheet.Button key={index} onPress={this._onSelectSheet} index={index}>{title}</ActionSheet.Button>
              })
            }
          </ActionSheet>
        </View>
      </View>
    );
  };
}

const styles = StyleSheet.create({
  main: {
    backgroundColor: '#fff',
    flex:1,
  },
  content: {
    flex: 1,
    //backgroundColor: 'red',
  },
  mapView: {
    height: 200,
  },
  addressPanel: {
    padding:10,
  },
  address: {
    color: '#cccccc',
  },
  rowItem: {
    padding: 10,
    flexDirection: 'row',
  },
  rowItemTitle: {
    color: 'black',
  },
  rowItemForward: {
    color: '#ccc',
    textAlign: 'right',
    flex: 1,
  }
});

module.exports = CheckIn;
