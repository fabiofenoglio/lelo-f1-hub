import { Component, OnInit, OnDestroy } from '@angular/core';
import { LeloF1SDKService } from 'app/core/lelo-f1-sdk/lelo-f1-sdk.service';
import { LeloF1NotificationHandler, LeloF1ButtonsStatus } from 'app/core/lelo-f1-sdk/lelo-f1-sdk.constants';
import { NGXLogger } from 'ngx-logger';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SequencePickerModalComponent } from './sequence-picker-modal/sequence-picker-modal.component';
import { ISequence } from 'app/shared/model/sequence.model';
import { SequencePlayerModalComponent } from './sequence-player-modal/sequence-player-modal.component';


@Component({
  selector: 'jhi-application-basic',
  templateUrl: './application-basic.component.html',
  styleUrls: ['./application-basic.component.scss']
})
export class ApplicationBasicComponent implements OnInit, OnDestroy {
  
  BATTERY_SAVING_TRESHOLD = 15;
  SCALE_SPEED_MIN = 30;
  SCALE_SPEED_STEP = 5;

  BUTTONS_ASSIGNMENT_MAIN_MOTOR = 1;
  BUTTONS_ASSIGNMENT_VIBE_MOTOR = 2;
  BUTTONS_ASSIGNMENT_BOTH_MOTORS = 3;

  errors: string[] = [];
  pending = 0;
  authorized = false;
  manufacturerName: string | null = null;
  modelNumber: string | null = null;
  hardwareRevision: string | null = null;
  firmwareRevision: string | null = null;
  softwareRevision: string | null = null;
  acceleration: number[] | null = null;
  mainMotorLevel: number | null = null;
  vibeMotorLevel: number | null = null;
  cruiseControlStatus = false;
  cruiseControlLevels: number[] | null = null;
  depth : number | null = null;
  batteryLevel: number | null = null;
  batterySaving = false;
  buttonsStatus: LeloF1ButtonsStatus | null = null;
  temperature: number | null = null;
  pressure: number | null = null;
  rotationSpeed: number | null = null;

  buttonsAssignment = this.BUTTONS_ASSIGNMENT_VIBE_MOTOR;

  playingSequence = false;
  activeSequenceController: SequencePlayerModalComponent | null = null;

  cruiseControlModal = {
      enabled: false,
      levels: [30, 40, 50, 60, 70, 80, 90, 100]
  };

  private notifications: LeloF1NotificationHandler<any>[] = [];
  private sensorNotifications: LeloF1NotificationHandler<any>[] = [];

  constructor( private logger: NGXLogger, 
    private modalService: NgbModal,
    private client: LeloF1SDKService ) {
  }
  
  ngOnInit(): void {
    this.clearConnection();

    setInterval(() => this.checkConnectionStatus, 2000);
    setInterval(() => this.checkMotorsLevelOnCruiseControl, 1000);
    setInterval(() => this.checkBatteryStatus, 5000);
  }

  ngOnDestroy(): void {
    // NOP
  }
  
  private refresh(): void {
    setTimeout(() => {
        // TODO ???
    });
  }

  isConnected(): boolean {
      return this.client && this.client.isConnected();
  }

  connect(): void {
    this.pending ++;
    this.refresh();

      this.logger.log('connecting to device ...');
      this.client.searchAndConnect().then(() => {
          this.logger.log('device connected!');
          this.pending --;
          this.authorized = false;

          this.readDeviceInfo();
          this.subscribeNotifications();
          this.checkMotorsLevel();
          this.refresh();

      }, err => {
        this.pending --;
        this.error('error connecting', err);
      });
  };

  disconnect(): void {
    this.pending ++;
    this.refresh();

      this.logger.log('disconnecting from device ...');
      this.client.disconnect().then(() => {
          this.logger.log('device disconnected');
          this.pending --;
          this.clearConnection();

      }, err => {
        this.pending --;
        this.error('error disconnecting', err);
      });
  };

  shutdown(): void {
    this.pending ++;
    this.refresh();

      this.logger.log('shutting down device ...');
      this.client.shutdown().then(() => {
          this.logger.log('device shutdown and disconnected');
          this.pending --;
          this.clearConnection();

      }, err => {
        this.pending --;
        this.error('error shutting down device', err);
      });
  };

  incrementMainMotor(): void {
      let toSet = (this.mainMotorLevel || 0) + this.SCALE_SPEED_STEP;
      if (toSet > 100) {
          toSet = 100;
      } else if (toSet < this.SCALE_SPEED_MIN) {
          toSet = this.SCALE_SPEED_MIN;
      }
      this.client.setMotorsSpeed(this.toDeviceSpeed(toSet), this.toDeviceSpeed(this.vibeMotorLevel || 0)).then(() => {
        this.mainMotorLevel = toSet;
        this.refresh();
      });
  };

  decrementMainMotor(): void {
      let toSet = (this.mainMotorLevel || 0) - this.SCALE_SPEED_STEP;
      if (toSet < this.SCALE_SPEED_MIN) {
          toSet = 0;
      }
      this.client.setMotorsSpeed(this.toDeviceSpeed(toSet), this.toDeviceSpeed(this.vibeMotorLevel || 0)).then(() => {
        this.mainMotorLevel = toSet;
          this.refresh();
      });
  };

  stopMainMotor(): void {
      this.client.setMotorsSpeed(0, this.toDeviceSpeed(this.vibeMotorLevel || 0)).then(() => {
        this.mainMotorLevel = 0;
        this.refresh();
      });
  };

  incrementVibeMotor(): void {
      let toSet = (this.vibeMotorLevel || 0) + this.SCALE_SPEED_STEP;
      if (toSet > 100) {
          toSet = 100;
      } else if (toSet < this.SCALE_SPEED_MIN) {
          toSet = this.SCALE_SPEED_MIN;
      }
      this.client.setMotorsSpeed(this.toDeviceSpeed(this.mainMotorLevel || 0), this.toDeviceSpeed(toSet)).then(() => {
        this.vibeMotorLevel = toSet;
        this.refresh();
      });
  };

  decrementVibeMotor(): void {
      let toSet = (this.vibeMotorLevel || 0) - this.SCALE_SPEED_STEP;
      if (toSet < this.SCALE_SPEED_MIN) {
          toSet = 0;
      }
      this.client.setMotorsSpeed(this.toDeviceSpeed(this.mainMotorLevel || 0), this.toDeviceSpeed(toSet)).then(() => {
        this.vibeMotorLevel = toSet;
      });
  };

  stopVibeMotor(): void {
    this.client.setMotorsSpeed(this.mainMotorLevel || 0, 0).then(() => {
      this.vibeMotorLevel = 0;
      this.refresh();
    });
  };

  stopMotors(): Promise<void> {
    return this.client.setMotorsSpeed(0, 0).then(() => {
      this.mainMotorLevel = 0;
      this.vibeMotorLevel = 0;
      this.refresh();
    });
  };

  incrementBothMotors(): void {
      let toSetMain = (this.mainMotorLevel || 0) + this.SCALE_SPEED_STEP;
      if (toSetMain > 100) {
          toSetMain = 100;
      } else if (toSetMain < this.SCALE_SPEED_MIN) {
          toSetMain = this.SCALE_SPEED_MIN;
      }

      let toSetVibe = (this.vibeMotorLevel || 0) + this.SCALE_SPEED_STEP;
      if (toSetVibe > 100) {
          toSetVibe = 100;
      } else if (toSetVibe < this.SCALE_SPEED_MIN) {
          toSetVibe = this.SCALE_SPEED_MIN;
      }
      this.client.setMotorsSpeed(this.toDeviceSpeed(toSetMain), this.toDeviceSpeed(toSetVibe)).then(() => {
        this.mainMotorLevel = toSetMain;
        this.vibeMotorLevel = toSetVibe;
        this.refresh();
      });
  };

  decrementBothMotors(): void {
      let toSetMain = (this.mainMotorLevel || 0) - this.SCALE_SPEED_STEP;
      if (toSetMain < this.SCALE_SPEED_MIN) {
          toSetMain = 0;
      }

      let toSetVibe = (this.vibeMotorLevel || 0) - this.SCALE_SPEED_STEP;
      if (toSetVibe < this.SCALE_SPEED_MIN) {
          toSetVibe = 0;
      }
      this.client.setMotorsSpeed(this.toDeviceSpeed(toSetMain), this.toDeviceSpeed(toSetVibe)).then(() => {
        this.mainMotorLevel = toSetMain;
        this.vibeMotorLevel = toSetVibe;
        this.refresh();
      });
  };

  centralButtonPressed(): void {
      if (!this.isConnected() || !this.authorized) {
          return;
      }

      if (this.playingSequence) {
        return;
      }
      
      if (!this.cruiseControlStatus) {
          if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_VIBE_MOTOR) {
            this.buttonsAssignment = this.BUTTONS_ASSIGNMENT_MAIN_MOTOR;

          } else if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_MAIN_MOTOR) {
            this.buttonsAssignment = this.BUTTONS_ASSIGNMENT_BOTH_MOTORS;

          } else {
            this.buttonsAssignment = this.BUTTONS_ASSIGNMENT_VIBE_MOTOR;
          }
      }

      this.refresh();
  };

  plusButtonPressed(): void {
      if (!this.isConnected() || !this.authorized) {
          return;
      }
      
      if (this.playingSequence) {
        return;
      }
      
      if (!this.cruiseControlStatus) {
          if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_VIBE_MOTOR) {
            this.incrementVibeMotor();
          } else if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_MAIN_MOTOR) {
            this.incrementMainMotor();
          } else if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_BOTH_MOTORS) {
            this.incrementBothMotors();
          }
      }

      this.refresh();
  };

  minusButtonPressed(): void {
      if (!this.isConnected() || !this.authorized) {
          return;
      }

      if (!this.cruiseControlStatus) {
          if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_VIBE_MOTOR) {
            this.decrementVibeMotor();
          } else if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_MAIN_MOTOR) {
            this.decrementMainMotor();
          } else if (this.buttonsAssignment === this.BUTTONS_ASSIGNMENT_BOTH_MOTORS) {
            this.decrementBothMotors();
          }
      }

      this.refresh();
  };

  onAuthorized(): void {
      this.logger.log('connection authorized')

      setTimeout(() => {
          this.client.shutdownMotors();
          this.checkMotorsLevel();
          this.checkCruiseControl();
          this.refresh();
      }, 500);
      
      this.checkMotorsLevel();
      this.refresh();
  }

  toLocalSpeed(deviceSpeed: number): number {
      return deviceSpeed;
  }

  toDeviceSpeed(localSpeed: number): number {
      return localSpeed;
  }

  checkMotorsLevel(): void {
      if (this.isConnected()) {
        this.client.getMotorsSpeed().then(speeds => {
          this.mainMotorLevel = this.toLocalSpeed(speeds[0]);
          this.vibeMotorLevel = this.toLocalSpeed(speeds[1]);
          this.refresh();
          });
      }
  }

  checkCruiseControl(): void {
      if (this.isConnected()) {
        this.client.getCruiseControl().then(cruiseControlStatus => {
          this.cruiseControlStatus = cruiseControlStatus;
          this.cruiseControlModal.enabled = cruiseControlStatus;
          this.refresh();
          });
          this.client.getVibrationSettings().then(values => {
            this.cruiseControlLevels = values;
            this.cruiseControlModal.levels = values;
          });
      }
  }

  checkConnectionStatus(): void {
      if (this.isConnected()) {
        this.client.ping().then(() => {
              // still connected
          }, err => {
              // device not responding
              setTimeout(() => {
                  if (this.isConnected()) {
                    this.error('device is not responding', err);
                      this.authorized = false;
                      this.client.disconnect();
                      this.clearConnection();
                  }
              }, 500);
          });
      }
  }

  checkMotorsLevelOnCruiseControl(): void {
      if (this.isConnected()) {
        this.checkMotorsLevel();
      }
  }

  checkBatteryStatus(): void {
      if (this.isConnected()) {
        this.client.getBatteryLevel().then(batteryLevel => {
          this.batteryLevelChanged(batteryLevel);
        });
      }
  }

  subscribeNotifications(): void {
    this.depth = 0;

    this.client.notifyKeyState(v => this.keyStateChanged(v)).then(v => this.toArray(this.notifications)(v));
    this.client.notifyButtons(v => this.buttonsChanged(v)).then(v => this.toArray(this.notifications)(v));

    this.client.notifyAccelerometer(v => this.toScope('acceleration')(v)).then(v => this.toArray(this.sensorNotifications)(v));
    this.client.notifyInsertionDepthPercentage(v => this.toScope('depth')(v)).then(v => this.toArray(this.sensorNotifications)(v));
    this.client.notifyRotationSpeed(v => this.rotationSpeedChanged(v)).then(v => this.toArray(this.sensorNotifications)(v));
    this.client.notifyTemperatureAndPressure(v => this.temperatureAndPressureChanged(v)).then(v => this.toArray(this.sensorNotifications)(v));
  }

  batteryLevelChanged(value: number): void {
    this.batteryLevel = value;
      if (!this.batterySaving) {
          if (value < this.BATTERY_SAVING_TRESHOLD) {
            this.enterBatterySaving();
          }
      }
      this.refresh();
  }

  enterBatterySaving(): void {
    this.batterySaving = true;
      for (const notification of this.sensorNotifications) {
          notification.unregister();
      }
      this.sensorNotifications.length = 0;
      this.logger.log('entering power saving mode');
      this.refresh();
  }

  keyStateChanged(value: boolean): void {
      const previous = this.authorized;
      this.authorized = value;
      if (value && !previous) {
          // just authorized
          this.onAuthorized();
      }
      this.refresh();
  }

  buttonsChanged(value: LeloF1ButtonsStatus): void {
    this.buttonsStatus = value;
    
    if (this.playingSequence) {
      this.activeSequenceController?.dispatchButtonPress(value);
      return;
    }
    
    if (this.authorized) {
        if (value.central) {
          this.centralButtonPressed();
        } else if (value.minus) {
          this.minusButtonPressed();
        } else if (value.plus) {
          this.plusButtonPressed();
        }
    }

    this.refresh();
  }

  temperatureAndPressureChanged(value: number[]): void {
    this.temperature = value[0];
    this.pressure = value[1];
    this.refresh();
  }

  rotationSpeedChanged(value: number): void {
    this.rotationSpeed = value;
  }

  readDeviceInfo(): void {
    this.client.getBatteryLevel().then(v => this.batteryLevelChanged(v));
    this.client.getManufacturerName().then(v => this.toScope('manufacturerName')(v));
    this.client.getModelNumber().then(v => this.toScope('modelNumber')(v));
    this.client.getHardwareRevision().then(v => this.toScope('hardwareRevision')(v));
    this.client.getFirmwareRevision().then(v => this.toScope('firmwareRevision')(v));
    this.client.getSoftwareRevision().then(v => this.toScope('softwareRevision')(v));
  }

  clearConnection(): void {
    this.errors = [];
    this.authorized = false;
    this.batterySaving = false;
      
    this.depth = null;
    this.manufacturerName = null;
    this.modelNumber = null;
    this.hardwareRevision = null;
    this.firmwareRevision = null;
    this.softwareRevision = null;
    this.acceleration = null;
    this.depth = null;
    this.buttonsStatus = null;
    this.temperature = null;
    this.pressure = null;
    this.batteryLevel = null;

    this.rotationSpeed = null;
    this.mainMotorLevel = null;
    this.vibeMotorLevel = null;
    this.cruiseControlStatus = false;
    this.cruiseControlLevels = null;
    this.buttonsStatus = null;

    this.notifications.length = 0;
    this.sensorNotifications.length = 0;

    this.refresh();
  }

  toScope(name: string): any {
      return (value: any) => {
          this[name] = value;
          this.refresh();
      }
  }

  toArray(target: any[]): any {
      return (value: any) => {
          target.push(value);
      }
  }

  error(prefix: string, err: any = null): void {
      this.logger.error(prefix, err)
      this.errors.push(prefix + ': ' + err);
      this.refresh();
      setTimeout(() => {
        this.errors.splice(0, 1);
        this.refresh();
      }, 5000);
  }

  pickSequence(): void {
    const modal = this.modalService.open(SequencePickerModalComponent, {
      backdrop: true,
      keyboard: true,
      size: 'lg'
    });

    modal.result.then(result => {
      this.stopMotors().then(() => {
        this.playSequence(result);
      });
    }, () => {});
  }

  playSequence(sequence: ISequence): void {
    this.playingSequence = true;

    const modal = this.modalService.open(SequencePlayerModalComponent, {
      backdrop: 'static',
      keyboard: false,
      size: 'lg'
    });

    this.activeSequenceController = (modal.componentInstance as SequencePlayerModalComponent);
    this.activeSequenceController.activate(this.client, sequence);

    modal.result.then(() => {
      this.stopMotors();
      this.playingSequence = false;
      this.activeSequenceController = null;
      
    }, () => {
      this.playingSequence = false;
      this.activeSequenceController = null;
    });
  }

}
