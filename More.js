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


class More extends Component {
  render() {
    return(
      <View style={styles.main}>
        <View style={styles.navigationBar}>
          <Text style={styles.navBack}>
            返回
          </Text>
          <Text style={styles.navTitle}>
            更多
          </Text>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  main: {
    backgroundColor: '#cccccc',

  },
  navigationBar: {
    backgroundColor: '#000000',
    flexDirection: 'row',
    paddingTop: 20,
    paddingBottom: 10,
    paddingLeft: 5,
    paddingRight: 5,
  },
  navBack: {
    color: '#fff',
  },
  navTitle: {
    color: '#fff',
    textAlign: 'center',
    flex: 1,
  },

});



module.exports = More;
