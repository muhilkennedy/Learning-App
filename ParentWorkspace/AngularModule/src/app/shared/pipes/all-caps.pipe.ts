import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'allCaps'
})
export class AllCapsPipe implements PipeTransform {

  transform(value: string, args?: any): any {
    return value.toUpperCase();
  }

}
