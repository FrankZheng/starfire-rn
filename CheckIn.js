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


class CheckIn extends Component {
  render() {
    return (
      <View style={styles.main}>
        <NavigationBar title="签到" navigator={this.props.navigator}/>
        <View style={styles.content}>
        </View>
      </View>
    );
  };
}

const styles = StyleSheet.create({
  main: {
    backgroundColor: '#0000cc',
    flex:1,
  },
  content: {
    flex: 1,
    //backgroundColor: 'red',
  },
});

module.exports = CheckIn;
