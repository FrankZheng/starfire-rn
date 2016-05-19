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

import NavigationBar from './NavigationBar'


class More extends Component {
  render() {
    return(
      <View style={styles.main}>
        <NavigationBar title="更多" navigator={this.props.navigator}/>
        <View style={styles.content}>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  main: {
    backgroundColor: '#00cc00',
    flex:1,
  },
  content: {
    flex: 1,
    //backgroundColor: 'red',
  },

});



module.exports = More;
