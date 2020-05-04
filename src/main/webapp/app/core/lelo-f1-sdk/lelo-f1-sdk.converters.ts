import { leloF1SdkConstants, LeloF1ButtonsStatus } from './lelo-f1-sdk.constants';


export const leloF1SdkConverters = {
    TO_STRING(value: any): string { return leloF1SdkConstants.TEXT_DECODER.decode(value); },
    TO_BOOLEAN(value: any): boolean { return !!(value.getUint8(0)); },
    TO_UINT8(value: any): number { return value.getUint8(0); },
    TO_UINT16(value: any): number { return value.getUint16(0); },
    TO_DEPTH_PERCENTAGE(value: any): number { return value.getUint16(0) * 12.5; },
    TO_BUTTONS(value: any): LeloF1ButtonsStatus {
        const v = value.getUint8(0);
        return {
            any: v !== 3,
            minus: v === 2,
            plus: v === 1,
            central: v === 0,
            none: v === 3,
            value: v
        } as LeloF1ButtonsStatus;
    },
    TO_ACCELEROMETER(value: any): number[] {
        return [ value.getUint16(0), value.getUint16(2), value.getUint16(4) ];
    },
    TO_TEMPERATURE_AND_PRESSURE(value: any): number[] {
        return [
            (value.getUint16(1) + (value.getUint8(0)*256*256))/100.0,
            (value.getUint32(4) / 100.0)
        ];
    },
    TO_TEMPERATURE(value: any): number { return (value.getUint16(1) + (value.getUint8(0)*256*256))/100.0; },
    TO_PRESSURE(value: any): number { return (value.getUint32(4) / 100.0); }
};