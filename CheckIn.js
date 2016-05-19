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



class CheckIn extends Component {
  render() {
    return (
      <View>
        <TouchableWithoutFeedback onPress={this.props.navigator.pop}>
          <View>
            <Text>
              back
            </Text>
          </View>
        </TouchableWithoutFeedback>
        <Text>
          check in
        </Text>
      </View>
    );
  };
}

module.exports = CheckIn;
