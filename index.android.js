/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

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

import CheckIn from './CheckIn';
import More from './More';

class Navigation extends Component {
  render() {
    return (
      <Navigator initialRoute={{id: 'index'}} renderScene={this.navigatorRenderScene}/>
    );
  }

  navigatorRenderScene(route, navigator) {
    _navigator = navigator;
    switch (route.id) {
      case 'index':
        return (<Index navigator={navigator}/>);
      case 'check-in':
        return (<CheckIn navigator={navigator}/>);
      case 'more':
        return (<More navigator={navigator}/>);
      default:
    }
  }
}

class Index extends Component {
  render() {
    return (
      <View style={styles.main}>
        <View style={styles.header}>
          <Image source={require('./img/userimg.png')} style={styles.headimg}/>
          <View style={styles.userPanel}>
            <Text style={styles.userTitle}>
              郑晓强
            </Text>
            <Text style={styles.userInfo}>
              总部员工
            </Text>
          </View>
        </View>
        <View style={styles.menu}>
          <TouchableWithoutFeedback onPress={this.checkIn.bind(this)}>
            <View style={styles.menuItem}>
              <Image source={require('./img/menu-checkin.png')}
                     style={styles.menuItemImg}/>
              <Text>
                我要签到
              </Text>
            </View>
          </TouchableWithoutFeedback>
          <TouchableWithoutFeedback onPress={this.more.bind(this)}>
            <View style={styles.menuItem}>
              <Image source={require('./img/menu-more.png')} style={styles.menuItemImg}/>
              <Text>
                更多
              </Text>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </View>
    );
  }
  checkIn() {
    this.props.navigator.push({
      id: 'check-in',
    });
  }
  more() {
    this.props.navigator.push({
      id: 'more',
    });
  }
}

const styles = StyleSheet.create({
  main: {

  },

  header: {
    backgroundColor: '#00C9B1',
    flexDirection: 'row',
    height: 100,
  },
  headimg: {
    justifyContent: 'center',
  },
  userPanel: {

  },
  userTitle: {

  },
  userInfo: {

  },

  menu: {
    flex:1,
    flexDirection: 'row',
  },
  menuItem: {

  },
  menuItemImg: {
    width: 40,
    height: 40,
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('Navigation', () => Navigation);
