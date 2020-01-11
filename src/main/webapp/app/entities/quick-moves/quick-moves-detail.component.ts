import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuickMoves } from 'app/shared/model/quick-moves.model';

@Component({
  selector: 'jhi-quick-moves-detail',
  templateUrl: './quick-moves-detail.component.html'
})
export class QuickMovesDetailComponent implements OnInit {
  quickMoves: IQuickMoves | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quickMoves }) => {
      this.quickMoves = quickMoves;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
