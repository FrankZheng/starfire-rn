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

class NavigationBar extends Component {
  render() {
    return(
      <View style={styles.navigationBar}>
        <TouchableWithoutFeedback onPress={this.props.navigator.pop}>
        <View>
          <Text style={styles.navBack}>
            返回
          </Text>
        </View>
        </TouchableWithoutFeedback>
        <Text style={styles.navTitle}>
          {this.props.title}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
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


module.exports = NavigationBar;
