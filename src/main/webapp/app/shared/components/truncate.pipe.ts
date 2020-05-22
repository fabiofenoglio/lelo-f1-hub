import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
 name: 'truncate'
})

export class TruncatePipe implements PipeTransform {

    transform(value: string, argsRaw: any): string {
        if (!value) {
            return value;
        }
        const args = Array.isArray(argsRaw) ? argsRaw : [argsRaw];

        const limit = args.length > 0 ? parseInt(args[0], 10) : 20;
        const trail = args.length > 1 ? args[1] : '...';
        return value.length > limit ? value.substring(0, limit) + trail : value;
   }
}