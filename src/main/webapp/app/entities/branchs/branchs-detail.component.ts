import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBranchs } from 'app/shared/model/branchs.model';

@Component({
  selector: 'jhi-branchs-detail',
  templateUrl: './branchs-detail.component.html'
})
export class BranchsDetailComponent implements OnInit {
  branchs: IBranchs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branchs }) => {
      this.branchs = branchs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
