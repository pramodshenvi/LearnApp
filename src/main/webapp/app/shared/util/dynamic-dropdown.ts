import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'jhi-dynamic-dropdown',
  templateUrl: './dynamic-dropdown.html'
})
export class DynamicDropdownComponent implements OnInit, OnChanges {
  @Input() dropdownList:any;
  @Input() keyboardAction:any;
  @Output() selectedValueEmitter = new EventEmitter<string>();
  selectionPosition = 0;

  constructor() {}

  ngOnInit(): void {
    this.selectionPosition = 0;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes.keyboardAction) {
      switch(changes.keyboardAction.currentValue) {
        case "ArrowDown": {
          this.selectionPosition ++;
          if(this.selectionPosition >= this.dropdownList.length)
            this.selectionPosition = 0;
          break;
        }
        case "ArrowUp": {
          this.selectionPosition --;
          if(this.selectionPosition < 0)
            this.selectionPosition = this.dropdownList.length-1;
          break;
        }
        case "Enter": {
          this.sendSelectedValueToParent(this.dropdownList[this.selectionPosition].name);
          break;
        }
      }
    }
  }

  sendSelectedValueToParent(value: string): void {
    this.dropdownList = [];
    this.selectedValueEmitter.emit(value);
  }

}
