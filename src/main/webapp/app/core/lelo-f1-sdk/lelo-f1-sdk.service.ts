import { leloF1SdkConstants, leloF1SdkDeviceDefinitions, LeloF1ButtonsStatus, LeloF1NotificationHandler, LeloF1NotificationCallback } from './lelo-f1-sdk.constants';
import { leloF1SdkConverters } from './lelo-f1-sdk.converters';
import { Injectable } from '@angular/core';
import { NGXLogger } from 'ngx-logger';


@Injectable()
export class LeloF1SDKService {

  device = null;
  server = null;
  disposing = false;
  services: any = {
      'dummy-uuid': {
          name: 'dummy service',
          uuid: 'dummy-uuid',
          characteristics: {
              'dummy-char-uuid': {
                  name: 'dummy char',
                  uuid: 'dummy-char-uuid',
              }
          }
      }
  };
  
  lockIndex = 0;
  lockUUID = 0;
  notificationProgressive = 0;
  
  constructor(private logger: NGXLogger) {}

  isSupported(): boolean {
    const nav: any = navigator as any;
    return nav && nav.bluetooth && nav.bluetooth.requestDevice;
  }

  searchAndConnect(): Promise<any> {
      this.disposing = false;
      this.logger.debug('running search-and-connect routine');
      return this.requestDevice().then(device => {
          return this.connect(device);
      });
  }

  requestDevice(): Promise<any> {
    this.disposing = false;
    this.logger.debug('requesting devices. Browser window should popup');

      return (navigator as any).bluetooth.requestDevice({ filters: [{
          name: leloF1SdkConstants.DEVICE_NAME
        }],
        optionalServices: leloF1SdkConstants.OPTIONAL_SERVICES 
      }).then((result: any) => {
        this.logger.debug('devices request finished', result);
          return result;
      }, (err: any) => {
        this.logger.error('error in devices request', err);
          throw err;
      });
  }

  connect(device: any): any {
    this.disposing = false;
    this.logger.debug('attempting connection to device', device);

      return device.gatt.connect().then((server: any) => {
        this.logger.info('connected to device', device);
          this.services = {};

          return this._discoverServices(device, server).then(() => {
            this.logger.debug('services discovery completed', this.services);
            this.device = device;
            this.server = server;
            this.disposing = false;

              return server;
          }, (err2: any) => {
            this.logger.error('error in services discovery after connection', err2);
              throw err2;
          })
      }, (err: any) => {
        this.logger.error('error in device connection', err);
          throw err;
      });
  }

  disconnect(): Promise<any> {
    this.disposing = true;
      if (!this.device) {
        this.logger.debug('not disconnecting from device because it is not connected');    
          return Promise.resolve();
      }

      return this._unregisterAllNotificationHandlers().then(() => {
        this.logger.debug('disconnecting from device');

          const device = this.device!;
  
          this.device = null;
          this.server = null;
          this.services = {};
  
          (device as any).gatt.disconnect();
          
          return Promise.resolve().then(() => {
            this.logger.info('disconnected from device');
              return;
          }, err => {
            this.logger.error('error in disconnection', err);
              throw err;
          });
      });
  }

  // NEEDS TESTING
  shutdown(): Promise<any> {
    this.disposing = true;
    this.logger.debug('shutdown requested');
    this.logger.debug('unregistering all notifications');

      return this._unregisterAllNotificationHandlers().then(() => {
        this.logger.debug('shutting down device');

          const onShutdown = () => {
            this.logger.debug('shut down signal sent');
              const device = this.device!;
  
              this.device = null;
              this.server = null;
              this.services = {};

              (device as any).gatt.disconnect();

              return true;
          }

          return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL, 
              Uint8Array.of(1, 250), true).then(() => {
              return onShutdown();

          }, (err: any) => {
            this.logger.warn('error writing shutdown signal - this might be normal as a SHUTDOWN signal has been sent', err);
              return onShutdown();
          });
      });
  }

  _discoverServices(device: any, server: any): Promise<any> {
    this.logger.debug('discovering device services');

      const promises = [];
      for (const entry of leloF1SdkConstants.USED_SERVICES) {
        this.logger.debug('discovering device service', entry.name);

          promises.push(server.getPrimaryService(entry.uuid).then((service: any) => {
            this.logger.debug('discovery found service ' + entry.name);
              const serviceEntry = {
                  name: entry.name,
                  uuid: entry.uuid,
                  handler: service,
                  characteristics: {}
              };
              this.services[entry.uuid] = serviceEntry;
              return this._discoverServiceCharacteristics(entry, service).then((result: any) => {
                this.logger.debug('characteristics discovery for service ' + entry.name + ' completed', result);
                  return result;
              }, (errSD: any) => {
                this.logger.error('error in characteristics discovery for service ' + entry.name, errSD);
                  throw errSD;
              });

          }, (err: any) => {
            this.logger.error('error looking for service ' + entry.name, err);
              throw err;
          }));
      }

      return Promise.all(promises).then(() => {
        this.logger.debug('services discovery completed', this.services);
          return this.services;
      }, (errExt: any) => {
        this.logger.error('error in services discovery', errExt);
          throw errExt;
      });
  }

  _discoverServiceCharacteristics(entry: any, service: any): Promise<any> {
    this.logger.debug('discovering service ' + entry.name + ' characteristics');
      const charPromises = [];

      for (const charEntry of entry.characteristics) {
        this.logger.debug('discovering service characteristic', charEntry);
          
          charPromises.push(service.getCharacteristic(charEntry.uuid).then((characteristic: any) => {
            this.logger.debug('discovery found service ' + entry.name + ' characteristic ' + charEntry.uuid);
              const charToAdd = {
                  name: charEntry.name,
                  uuid: charEntry.uuid,
                  handler: characteristic
              };
              this.services[entry.uuid].characteristics[charEntry.uuid] = charToAdd;
              return characteristic;

          }, (charErr: any) => {
            this.logger.error('error looking for service ' + entry.name + ' characteristic ' + charEntry.uuid, charErr);
              throw charErr;
          }));
      }

      return Promise.all(charPromises).then(() => {
        this.logger.debug('characteristics discovery for service ' + entry.name + ' completed', this.services);
          return this.services;
      }, (errExt: any) => {
        this.logger.error('error in characteristics discovery for service ' + entry.name, errExt);
          throw errExt;
      });
  }

  _getService(uuid: string): any {
      const service = this.services[uuid];
      if (!service) {
          this.logger.warn('service ' + uuid + ' not found or not registered');
          return null;
      }
      return service;
  }

  _getCharacteristic(serviceUUID: string, uuid: string): any {
      const service = this._getService(serviceUUID);
      if (!service) {
          return null;
      }
      const characteristic = service.characteristics[uuid];
      if (!characteristic) {
          this.logger.warn('service ' + serviceUUID + ' characteristic ' + uuid + ' not found or not registered');
          return null;
      }
      return characteristic;
  }

  _withLock<T>(routineName: string, promiseBuilder: any, lockProperty = 'bleReadWrite'): Promise<T> {
      const lockUUID = ++this.lockUUID;

      this.logger.debug('processing routine with access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName);

      return new Promise((resolve, reject) => {
          let handler: any = null;
          let dispatched = false;

          const attemptDispatching = () => {
              if (!this[lockProperty]) {
                this[lockProperty] = 0;
              }

              if (this[lockProperty] > 0) {
                  return;
              }

              this[lockProperty] ++;
              dispatched = true;

              try {
                this.logger.debug('acquired BLE access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName);
                  const promise = promiseBuilder();

                  promise.then((cbResult: any) => {
                      if (handler) {
                          clearInterval(handler);
                      }
                      this[lockProperty] --;
                      this.logger.debug('released BLE access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName);
                      resolve(cbResult);
                  }, (cbError: any) => {
                      if (handler) {
                          clearInterval(handler);
                      }
                      this[lockProperty] --;
                      this.logger.warn('promise rejected inside BLE access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName, cbError);
                      this.logger.debug('released BLE access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName);
                      reject(cbError);
                  });

              } catch (err) {
                  if (handler) {
                      clearInterval(handler);
                  }
                  this.logger.error('error in routine inside BLE access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName, err);
                  this[lockProperty] --;
                  this.logger.debug('released BLE access lock ' + lockProperty + ':' + lockUUID + ' - ' + routineName);
                  reject(err);
              }
          }
      
          attemptDispatching();

          if (!dispatched) {
            this.logger.debug('BLE access lock ' + lockProperty + ':' + lockUUID + ' is busy. Will wait for lock.');
              handler = setInterval(() => {
                  attemptDispatching();
              }, 10);
          }
      });
  }

  _read(serviceUUID: string, charUUID: string): Promise<DataView> {
      try {
          const characteristic = this._getCharacteristic(serviceUUID, charUUID);
          if (!characteristic) {
              return Promise.reject('service ' + serviceUUID + ' characteristic ' + charUUID + ' not found or not registered');
          }

          return this._withLock('READ ' + characteristic.name, () => {
              return characteristic.handler.readValue().then((value: any) => {
                this.logger.debug('READ ' + serviceUUID + ':' + charUUID);
                  return value;
              }, (err: any) => {
                this.logger.warn('error reading from service ' + serviceUUID + ' characteristic ' + charUUID, err);
                  throw err;
              })
          });
      }
      catch(runtimeError) {
          return Promise.reject(runtimeError);
      }
  }

  _write<T>(serviceUUID: string, charUUID: string, value: T, lock = true): Promise<T> {
      try {
          const characteristic = this._getCharacteristic(serviceUUID, charUUID);
          if (!characteristic) {
              return Promise.reject('service ' + serviceUUID + ' characteristic ' + charUUID + ' not found or not registered');
          }

          const dispatcher = () => {
              return characteristic.handler.writeValue(value).then((wvalue: any) => {
                this.logger.debug('WRITE ' + serviceUUID + ':' + charUUID);
                return wvalue;
              }, (err: any) => {
                this.logger.warn('error writing to service ' + serviceUUID + ' characteristic ' + charUUID, err);
                  throw err;
              });
          };

          if (lock) {
              return this._withLock('WRITE ' + characteristic.name, dispatcher);
          } else {
              return dispatcher();
          }
      }
      catch(runtimeError) {
          return Promise.reject(runtimeError);
      }
  }

  getKeyState(): Promise<boolean> {
    return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.KEY_STATE)
        .then(leloF1SdkConverters.TO_BOOLEAN);
}

  waitForAuthorization(): Promise<boolean> {
      if (!this.server) {
          return Promise.reject('Device not connected');
      }

      const promise = new Promise<boolean>((resolve, reject) => {

          const handler = setInterval(() => {
            this.logger.debug('checking for key_state ...');
            this.getKeyState().then(keyState => {
                this.logger.debug('checking for key_state: ', keyState);
                  if (keyState) {
                      clearInterval(handler);
                      resolve(true);
                  }
              }, (err: any) => {
                this.logger.debug('checking for key_state threw error', err);
                  clearInterval(handler);
                  reject(err);
              });
          }, 500);
  
      });

      return promise;
  }

  isConnected(): boolean {
      return !!this.server && !this.disposing;
  }
  
  isAuthorized(): Promise<boolean> {
      if (!this.server) {
          return Promise.resolve(false);
      } else {
          return this.getKeyState();
      }
  }
  
  getManufacturerName(): Promise<string> {
      return this._read(leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE, leloF1SdkDeviceDefinitions.MANUFACTURER_NAME)
          .then(leloF1SdkConverters.TO_STRING);
  }

  getFirmwareRevision(): Promise<string> {
      return this._read(leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE, leloF1SdkDeviceDefinitions.FIRMWARE_REVISION)
          .then(leloF1SdkConverters.TO_STRING);
  }
  
  getHardwareRevision(): Promise<string> {
      return this._read(leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE, leloF1SdkDeviceDefinitions.HARDWARE_REVISION)
          .then(leloF1SdkConverters.TO_STRING);
  }
  
  getModelNumber(): Promise<string> {
      return this._read(leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE, leloF1SdkDeviceDefinitions.MODEL_NUMBER)
          .then(leloF1SdkConverters.TO_STRING);
  }

  getSoftwareRevision(): Promise<string> {
      return this._read(leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE, leloF1SdkDeviceDefinitions.SOFTWARE_REVISION)
          .then(leloF1SdkConverters.TO_STRING);
  }
  
  getBatteryLevel(): Promise<number> {
      return this._read('battery_service', 'battery_level')
          .then(leloF1SdkConverters.TO_UINT8);
  }

  getMotorsSpeed(): Promise<number[]> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL).then((value: DataView) => {
          return [value.getUint8(1), value.getUint8(2)];
      });
  }
  
  getMainMotorSpeed(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL).then((value: DataView) => {
          return value.getUint8(1);
      });
  }
  
  getVibratorSpeed(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL).then((value: DataView) => {
          return value.getUint8(2);
      });
  }
  
  setMotorsSpeed(motorSpeed: number, vibeSpeed: number): Promise<any> {
      if (motorSpeed === null || typeof motorSpeed ==='undefined' || vibeSpeed === null || typeof vibeSpeed ==='undefined') {
          return Promise.reject('Two values are required');
      }
      if (motorSpeed < 0 || motorSpeed > 100 || vibeSpeed < 0 || vibeSpeed > 100) {
          return Promise.reject('Values should be between 0 and 100');
      }
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL, Uint8Array.of(1, motorSpeed, vibeSpeed));
  }

  setMainMotorSpeed(motorSpeed: number): Promise<any> {
      if (motorSpeed === null || typeof motorSpeed ==='undefined') {
          return Promise.reject('Value is required');
      }
      if (motorSpeed < 0 || motorSpeed > 100) {
          return Promise.reject('Value should be between 0 and 100');
      }
      return this.getVibratorSpeed().then(otherSpeed => {
        this.logger.debug('other motor speed is' + otherSpeed);
          return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL, Uint8Array.of(1, motorSpeed, otherSpeed));
      });
  }

  setVibratorSpeed(vibeSpeed: number): Promise<any> {
      if (vibeSpeed === null || typeof vibeSpeed ==='undefined') {
          return Promise.reject('Value is required');
      }
      if (vibeSpeed < 0 || vibeSpeed > 100) {
          return Promise.reject('Value should be between 0 and 100');
      }
      return this.getMainMotorSpeed().then(otherSpeed => {
        this.logger.debug('other motor speed is' + otherSpeed);
          return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL, Uint8Array.of(1, otherSpeed, vibeSpeed));
      });
  }

  shutdownMotors(): Promise<any> {
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL, Uint8Array.of(1, 0, 0));
  }

  stop(): Promise<any> {
      /* ALIAS */
      return this.shutdownMotors();
  }

  // NEEDS TESTING
  verifyAccelerometer(): Promise<any> {
      this.logger.debug('entering accelerometer verification mode');
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_CONTROL, Uint8Array.of(255, 255, 255));
  }

  getUseCount(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.USER_RECORD)
          .then(leloF1SdkConverters.TO_UINT16);
  }
  
  resetUseCount(): Promise<any> {
      this.logger.debug('clearing usage counter');
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.USER_RECORD, Uint8Array.of(238));
  }
  
  getButtonsStatus(): Promise<LeloF1ButtonsStatus> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.BUTTON)
          .then(leloF1SdkConverters.TO_BUTTONS);
  }

  getButtons(): Promise<LeloF1ButtonsStatus> {
      /* ALIAS */
      return this.getButtonsStatus();
  }
  
  getTemperatureAndPressure(): Promise<number[]> {
    return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.PRESSURE)
        .then(leloF1SdkConverters.TO_TEMPERATURE_AND_PRESSURE);
  }

  getTemperature(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.PRESSURE)
          .then(leloF1SdkConverters.TO_TEMPERATURE);
  }
  
  getPressure(): Promise<number> {
    return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.PRESSURE)
        .then(leloF1SdkConverters.TO_PRESSURE);
    }

  getAccelerometerX(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.ACCELEROMETER).then(value => {
          return value.getUint16(0);
      });
  }

  getAccelerometerY(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.ACCELEROMETER).then(value => {
          return value.getUint16(2);
      });
  }

  getAccelerometerZ(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.ACCELEROMETER).then(value => {
          return value.getUint16(4);
      });
  }
  
  getAccelerometer(): Promise<number[]> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.ACCELEROMETER)
          .then(leloF1SdkConverters.TO_ACCELEROMETER);
  }

  getDepth(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.LENGTH)
          .then(leloF1SdkConverters.TO_UINT16);
  }
  
  getInsertionDepth(): Promise<number> {
      /* ALIAS */
      return this.getDepth();
  }
  
  getInsertionDepthPercentage(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.LENGTH)
          .then(leloF1SdkConverters.TO_DEPTH_PERCENTAGE);
  }

  getRotationSpeed(): Promise<number> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.HALL)
          .then(leloF1SdkConverters.TO_UINT16);
  }
  
  ping(): Promise<true> {
      return this.getKeyState().then(() => {
          return true;
      });
  }
  
  getWakeUp(): Promise<boolean> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.WAKE_UP)
          .then(leloF1SdkConverters.TO_BOOLEAN);
  }
  
  // NEEDS TESTING
  enableWakeUp(): Promise<any> {
      this.logger.debug('enabling quick wake-up');
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.WAKE_UP, Uint8Array.of(1));
  }

  // NEEDS TESTING
  disableWakeUp(): Promise<any> {
      this.logger.debug('disabling quick wake-up');
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.WAKE_UP, Uint8Array.of(0));
  }
  
  getCruiseControl(): Promise<boolean> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_WORK_ON_TOUCH)
          .then(leloF1SdkConverters.TO_BOOLEAN);
  }

  // NEEDS TESTING
  enableCruiseControl(resetSpeed = false): Promise<any> {
      this.logger.debug('enabling cruise control mode');
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_WORK_ON_TOUCH, Uint8Array.of(resetSpeed ? 2 : 1));
  }

  // NEEDS TESTING
  disableCruiseControl(): Promise<any> {
      this.logger.debug('disabling cruise control mode');
      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.MOTOR_WORK_ON_TOUCH, Uint8Array.of(0));
  }

  getVibrationSettings(): Promise<number[]> {
      return this._read(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.VIBRATOR_SETTING).then(value => {
          return [
              value.getUint8(7),
              value.getUint8(6),
              value.getUint8(5),
              value.getUint8(4),
              value.getUint8(3),
              value.getUint8(2),
              value.getUint8(1),
              value.getUint8(0)
          ];
      });
  }
  
  // NEEDS TESTING
  setVibrationSettings(values: number[]): Promise<any> {
      if (values === null || typeof values ==='undefined') {
          return Promise.reject('Values are required');
      } else if (values.length !== 8) {
          return Promise.reject('Exactly 8 values are required');
      }
      for (const val of values) {
          if (val < 0 || val > 100) {
              return Promise.reject('Values should be between 0 and 100');
          }
      }

      return this._write(leloF1SdkDeviceDefinitions.LELO_SERVICE, leloF1SdkDeviceDefinitions.VIBRATOR_SETTING, 
          Uint8Array.of( values[7], values[6], values[5], values[4], values[3], values[2], values[1], values[0] ));
  }
  
  notifyButtons(callback: LeloF1NotificationCallback<LeloF1ButtonsStatus>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<LeloF1ButtonsStatus>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.BUTTON,
          callback,
          leloF1SdkConverters.TO_BUTTONS,
          distinctUntilChanged );
  }
  
  notifyInsertionDepth(callback: LeloF1NotificationCallback<number>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.LENGTH,
          callback,
          leloF1SdkConverters.TO_UINT16,
          distinctUntilChanged );
  }
  
  notifyInsertionDepthPercentage(callback: LeloF1NotificationCallback<number>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.LENGTH,
          callback,
          leloF1SdkConverters.TO_DEPTH_PERCENTAGE,
          distinctUntilChanged );
  }
  
  notifyKeyState(callback: LeloF1NotificationCallback<boolean>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<boolean>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.KEY_STATE,
          callback,
          leloF1SdkConverters.TO_BOOLEAN,
          distinctUntilChanged );
  }
  
  notifyRotationSpeed(callback: LeloF1NotificationCallback<number>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.HALL,
          callback,
          leloF1SdkConverters.TO_UINT16,
          distinctUntilChanged );
  }
  
  notifyAccelerometer(callback: LeloF1NotificationCallback<number[]>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number[]>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.ACCELEROMETER,
          callback,
          leloF1SdkConverters.TO_ACCELEROMETER,
          distinctUntilChanged );
  }
  
  notifyTemperatureAndPressure(callback: LeloF1NotificationCallback<number[]>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number[]>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.PRESSURE,
          callback,
          leloF1SdkConverters.TO_TEMPERATURE_AND_PRESSURE,
          distinctUntilChanged );
  }
  
  notifyTemperature(callback: LeloF1NotificationCallback<number>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.PRESSURE,
          callback,
          leloF1SdkConverters.TO_TEMPERATURE,
          distinctUntilChanged );
  }
  
  notifyPressure(callback: LeloF1NotificationCallback<number>, distinctUntilChanged = true): Promise<LeloF1NotificationHandler<number>> {
      return this._registerNotification(
          leloF1SdkDeviceDefinitions.LELO_SERVICE,
          leloF1SdkDeviceDefinitions.PRESSURE,
          callback,
          leloF1SdkConverters.TO_PRESSURE,
          distinctUntilChanged );
  }
  
  _buildNotificationHandler<T>(characteristic: any, callback: LeloF1NotificationCallback<T>, distinctUntilChanged: boolean): LeloF1NotificationHandler<T> {
      const o: LeloF1NotificationHandler<T> = {
        characteristic,
        id: ++this.notificationProgressive,
        active: false,
        userCallback: callback,
        distinctUntilChanged,
        lastDataBuffer: null,
        _listener(): any {},
        unregister(): any {},
        _dispatcher(): any {}
    };

    o._listener = (value: T, event: any) => {
        if (!o.active || !o.userCallback) {
            return;
        }
        o.userCallback(value, event);
    };

    o.unregister = () => {
        return this.unregister.apply(this, [o]);
    };

    return o;
  }

  _registerNotification<T>(serviceUUID: string, charUUID: string, callback: LeloF1NotificationCallback<T>, converter: any, distinctUntilChanged: boolean): Promise<LeloF1NotificationHandler<T>> {

      const characteristic = this._getCharacteristic(serviceUUID, charUUID);
      if (!characteristic) {
          return Promise.reject('service ' + serviceUUID + ' characteristic ' + charUUID + ' not found or not registered');
      }

      return this._withLock('REGISTERING ' + characteristic.name, () => {
        this.logger.debug('registering notifications callback for characteristic ' + charUUID);
          const handler = this._buildNotificationHandler(characteristic, callback, distinctUntilChanged);

          let beRegisterHandle;

          if (!characteristic.notifying) {
            this.logger.debug('enabling channel notifications for characteristic ' + charUUID);
              beRegisterHandle = characteristic.handler.startNotifications().then((result: any) => {
                this.logger.debug('enabled channel notifications for characteristic ' + charUUID);
                  return result;
              });
          } else {
              beRegisterHandle = Promise.resolve(true);
          }

          return beRegisterHandle.then(() => {
              characteristic.notifying = true;

              if (!characteristic.notificationHandlers) {
                  characteristic.notificationHandlers = [];
              }

              handler._dispatcher = (event) => {
                  const convertedValue = converter && event && event.target && event.target.value ?
                      converter(event.target.value) : undefined;

                  if (distinctUntilChanged && event.target && event.target.value) {
                      if (handler.lastDataBuffer && this._callbackBufferEquals(event.target.value.buffer, handler.lastDataBuffer)) {
                          return;
                      }
                      handler.lastDataBuffer = event.target.value.buffer;
                  }

                  handler._listener.apply(handler, [convertedValue, event]);
              }
              
              characteristic.handler.addEventListener(leloF1SdkConstants.EVENTS.CHARACTERISTIC_VALUE_CHANGED, handler._dispatcher);
              characteristic.notificationHandlers.push(handler);

              this.logger.debug('there are now ' + characteristic.notificationHandlers.length + 
                  ' registered notification callbacks for characteristic ' + charUUID);

              handler.active = true;
              return handler;
          }, (err: any) => {
            this.logger.error('error enabling channel notifications for characteristic ' + charUUID, err);
              throw err;
          })

      });
  }

  unregister(handler: LeloF1NotificationHandler<any>): Promise<any> {
      if (!handler) {
          return Promise.reject('handler is required');
      }

      const characteristic = handler.characteristic;

      return this._withLock('UNREGISTERING ' + characteristic.name, () => {
        this.logger.debug('unregistering notifications callback for characteristic ' + characteristic.uuid);

          handler.active = false;
          characteristic.notificationHandlers.splice(characteristic.notificationHandlers.indexOf(handler), 1);
          characteristic.handler.removeEventListener(leloF1SdkConstants.EVENTS.CHARACTERISTIC_VALUE_CHANGED, handler._dispatcher);

          this.logger.debug('there are now ' + characteristic.notificationHandlers.length + 
              ' registered notification callbacks for characteristic ' + characteristic.uuid);

          if (characteristic.notificationHandlers.length < 1) {
            this.logger.debug('disabling channel notifications for characteristic ' + characteristic.uuid);
              return characteristic.handler.stopNotifications().then(() => {
                this.logger.debug('disabled channel notifications for characteristic ' + characteristic.uuid);
                  return handler;
              });
          } else {
              return Promise.resolve(handler);
          }

      });
  }

  _unregisterAllNotificationHandlers(): Promise<any> {
    this.logger.debug('unregistering all callbacks');
      const promises = [];

      for (const service of Object.values(this.services)) {
          for (const characteristicRaw of Object.values((service as any).characteristics)) {
              const characteristic = characteristicRaw as any;
              if (characteristic.notificationHandlers && characteristic.notificationHandlers.length) {
                  for (const handler of characteristic.notificationHandlers) {
                      promises.push(this.unregister(handler).then(ok => {
                          return ok;
                      }, e => {
                        this.logger.warn('error unregistering callback', e);
                      }));
                  }
              }
          }
      }

      return Promise.all(promises).then(() => {
        this.logger.debug('unregistered all callbacks');

      }, err => {
        this.logger.error('error unregistering all callbacks', err);
          throw err;
      });
  }

  _callbackBufferEquals(buf1: any, buf2: any): boolean {
      if (!buf1 || !buf2) {
          return false;
      }
      if (buf1.byteLength !== buf2.byteLength) {
          return false;
      }
      const dv1 = new Int8Array(buf1);
      const dv2 = new Int8Array(buf2);
      for (let i = 0 ; i < buf1.byteLength ; i++) {
          if (dv1[i] !== dv2[i]) {
              return false;
          }
      }
      return true;
  }
  
  getDeviceName(): Promise<string> {
      // fixed - lookup implemented by name so ...
      return Promise.resolve(leloF1SdkConstants.DEVICE_NAME);
  }
  
  getMacAddress(): Promise<string> {
    return this._notImplemented();
  }
  
  getChipId(): Promise<string> {
    return this._notImplemented();
  }

  getIEEE1107320601(): Promise<string> {
    return this._notImplemented();
  }
  
  getPNPId(): Promise<string> {
    return this._notImplemented();
  }
  
  getSerialNumber(): Promise<string> {
    return this._notImplemented();
  }
  
  getSystemId(): Promise<string> {
    return this._notImplemented();
  }
  
  _notImplemented(): Promise<any> {
    return Promise.reject('NOT IMPLEMENTED - but working on it!');
  }
}
