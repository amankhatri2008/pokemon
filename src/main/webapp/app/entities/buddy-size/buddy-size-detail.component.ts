import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBuddySize } from 'app/shared/model/buddy-size.model';

@Component({
  selector: 'jhi-buddy-size-detail',
  templateUrl: './buddy-size-detail.component.html'
})
export class BuddySizeDetailComponent implements OnInit {
  buddySize: IBuddySize | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ buddySize }) => {
      this.buddySize = buddySize;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
