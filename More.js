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
var AppInfo = require('./AppInfo');
var log = require('./Log');

const TAG = "More";

class InfoRow extends Component {
  render() {
    return(
      <View style={styles.infoRow}>
        <Text style={styles.title}>
          {this.props.title}
        </Text>
        <Text style={styles.value}>
          {this.props.value}
        </Text>
      </View>
    );
  }
}

class InfoRowCenter extends Component {
  render() {
    return(
      <TouchableWithoutFeedback onPress={this.props.onPress}>
      <View style={styles.infoRow}>
        <Text style={styles.titleCenter}>
          {this.props.title}
        </Text>
      </View>
      </TouchableWithoutFeedback>
    );
  }
}


class More extends Component {
  constructor(props) {
    super(props);
    this.state = {
      appVersion: null,
    }
  }

  componentDidMount() {
    this.getAppInfo();
  }

  getAppInfo() {
    AppInfo.getAppInfo((info) => {
      this.setState({
        appVersion: info.appVersion,
      });
    });
  }


  render() {
    return(
      <View style={styles.main}>
        <NavigationBar title="更多" navigator={this.props.navigator}/>
        <View style={styles.content}>
          <Image source={require('./img/logo.png')} style={styles.logo}/>
          <InfoRow title="当前版本号" value={this.state.appVersion}/>
          <InfoRowCenter title="退出登录"/>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  main: {
    backgroundColor: '#fff',
    flex: 1,
  },
  content: {
    flex: 1,
    //backgroundColor: 'red',
  },
  logo: {
    alignSelf: 'center',
    width: 60,
    height: 60,
  },
  infoRow: {
    flexDirection: 'row',
    paddingLeft: 10,
    paddingRight: 10,
    paddingTop: 10,
    paddingBottom: 10,
    borderColor: '#ccc',
    borderTopWidth: 1,
    borderBottomWidth: 1,
  },

  title: {
    textAlign:'left',
  },

  value: {
    flex: 1,
    textAlign:'right',
  },

  titleCenter: {
    textAlign:'center',
    alignSelf:'center',
    color: '#cc0000',
    flex:1,
  }

});



module.exports = More;
