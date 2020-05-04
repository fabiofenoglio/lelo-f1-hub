export const leloF1SdkDeviceDefinitions = {
    LELO_SERVICE: '0000fff0-0000-1000-8000-00805f9b34fb',
    DEVICE_INFORMATION_SERVICE: '0000180a-0000-1000-8000-00805f9b34fb',
	GENERIC_ACCESS_PROFILE: '00001800-0000-1000-8000-00805f9b34fb',
	GENERIC_ATTRIBUTE_PROFILE: '00001801-0000-1000-8000-00805f9b34fb',

    MOTOR_CONTROL: '0000fff1-0000-1000-8000-00805f9b34fb',
    KEY_STATE:  '00000a0f-0000-1000-8000-00805f9b34fb',
    USER_RECORD: '00000a04-0000-1000-8000-00805f9b34fb',
    BUTTON: '00000aa4-0000-1000-8000-00805f9b34fb',
    PRESSURE: '00000a0a-0000-1000-8000-00805f9b34fb',
    ACCELEROMETER: '00000a0c-0000-1000-8000-00805f9b34fb',
    LENGTH: '00000a0b-0000-1000-8000-00805f9b34fb',
    HALL: '00000aa3-0000-1000-8000-00805f9b34fb',
    WAKE_UP: '00000aa1-0000-1000-8000-00805f9b34fb',
    MOTOR_WORK_ON_TOUCH: '00000aa5-0000-1000-8000-00805f9b34fb',
	VIBRATOR_SETTING: '00000a0d-0000-1000-8000-00805f9b34fb',
    
    MANUFACTURER_NAME: '00002a29-0000-1000-8000-00805f9b34fb',
	MODEL_NUMBER: '00002a24-0000-1000-8000-00805f9b34fb',
	HARDWARE_REVISION: '00002a27-0000-1000-8000-00805f9b34fb',
	FIRMWARE_REVISION: '00002a26-0000-1000-8000-00805f9b34fb',
    SOFTWARE_REVISION: '00002a28-0000-1000-8000-00805f9b34fb',
    
    BATTERY_LEVEL: '00002a19-0000-1000-8000-00805f9b34fb',
    CHIP_ID: '00000a07-0000-1000-8000-00805f9b34fb',
    MAC_ADDRESS: '00000a06-0000-1000-8000-00805f9b34fb',
	SERIAL_NUMBER: '00000a05-0000-1000-8000-00805f9b34fb',
};

export const leloF1SdkConstants = {
    DEVICE_NAME: 'F1s',
    USED_SERVICES: [{
        name: 'battery service',
        uuid: 'battery_service',
        characteristics: [{
            name: 'battery level',
            uuid: 'battery_level'
        }]
    }, {
        name: 'DeviceInformation service',
        uuid: leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE,
        characteristics: [
            { name: 'MANUFACTURER NAME', uuid: leloF1SdkDeviceDefinitions.MANUFACTURER_NAME },
            { name: 'MODEL NUMBER', uuid: leloF1SdkDeviceDefinitions.MODEL_NUMBER },
            { name: 'HARDWARE REVISION', uuid: leloF1SdkDeviceDefinitions.HARDWARE_REVISION },
            { name: 'FIRMWARE REVISION', uuid: leloF1SdkDeviceDefinitions.FIRMWARE_REVISION },
            { name: 'SOFTWARE REVISION', uuid: leloF1SdkDeviceDefinitions.SOFTWARE_REVISION }
        ]
    }, {
        name: 'LELO service',
        uuid: leloF1SdkDeviceDefinitions.LELO_SERVICE,
        characteristics: [
            { name: 'KEY STATE', uuid: leloF1SdkDeviceDefinitions.KEY_STATE },
            { name: 'MOTOR CONTROL', uuid: leloF1SdkDeviceDefinitions.MOTOR_CONTROL },
            { name: 'USER RECORD', uuid: leloF1SdkDeviceDefinitions.USER_RECORD },
            { name: 'BUTTON', uuid: leloF1SdkDeviceDefinitions.BUTTON },
            { name: 'PRESSURE', uuid: leloF1SdkDeviceDefinitions.PRESSURE },
            { name: 'ACCELEROMETER', uuid: leloF1SdkDeviceDefinitions.ACCELEROMETER },
            { name: 'LENGTH', uuid: leloF1SdkDeviceDefinitions.LENGTH },
            { name: 'HALL', uuid: leloF1SdkDeviceDefinitions.HALL },
            { name: 'WAKE UP', uuid: leloF1SdkDeviceDefinitions.WAKE_UP },
            { name: 'CRUISE CONTROL', uuid: leloF1SdkDeviceDefinitions.MOTOR_WORK_ON_TOUCH },
            { name: 'VIBRATOR SETTING', uuid: leloF1SdkDeviceDefinitions.VIBRATOR_SETTING }
        ]
    }],
    OPTIONAL_SERVICES: [
        'battery_service',
        leloF1SdkDeviceDefinitions.DEVICE_INFORMATION_SERVICE,
        leloF1SdkDeviceDefinitions.LELO_SERVICE
    ],
    EVENTS: {
        CHARACTERISTIC_VALUE_CHANGED: 'characteristicvaluechanged'
    },
    TEXT_DECODER: new TextDecoder('utf-8'),
};

export interface LeloF1ButtonsStatus {
    any: boolean;
    minus: boolean;
    plus: boolean;
    central: boolean;
    none: boolean;
    value: boolean;
}

export type LeloF1NotificationCallback<T> = (value: T, event: any) => void;

export interface LeloF1NotificationHandler<T> {
    characteristic: any;
    id: number;
    active: boolean;
    userCallback: LeloF1NotificationCallback<T>;
    distinctUntilChanged: boolean;
    lastDataBuffer: any;
    _listener: (value: any, event: any) => void;
    unregister: () => void;
    _dispatcher: (event: any) => void;
}
