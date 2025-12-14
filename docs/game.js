// Bubble Bobble - HTML5 Canvas Port
const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

// Game dimensions
const GAME_WIDTH = 800;
const GAME_HEIGHT = 600;

// Scale for responsive design
let scale = 1;
let offsetX = 0;
let offsetY = 0;

// Game state
let gameStarted = false;
let keys = {};
let touchControls = { left: false, right: false, up: false, jump: false, shoot: false };

// Game objects
let player;
let enemies = [];
let bubbles = [];
let fruits = [];
let platforms = [];

// Physics
const GRAVITY = 0.5;
const JUMP_FORCE = -12;
const MOVE_SPEED = 5;
const BUBBLE_SPEED = 8;

// Platform layout
const PLATFORM_DATA = [
    { x: 50, y: 550, w: 300, h: 20 },
    { x: 450, y: 550, w: 300, h: 20 },
    { x: 150, y: 450, w: 200, h: 20 },
    { x: 450, y: 450, w: 200, h: 20 },
    { x: 50, y: 350, w: 250, h: 20 },
    { x: 500, y: 350, w: 250, h: 20 },
    { x: 250, y: 250, w: 300, h: 20 },
    { x: 100, y: 150, w: 200, h: 20 },
    { x: 500, y: 150, w: 200, h: 20 },
    // Walls
    { x: 0, y: 0, w: 20, h: 600 },
    { x: 780, y: 0, w: 20, h: 600 },
    { x: 0, y: 0, w: 800, h: 20 },
];

// Player class
class Player {
    constructor(x, y) {
        this.x = x;
        this.y = y;
        this.width = 48;
        this.height = 48;
        this.vx = 0;
        this.vy = 0;
        this.onGround = false;
        this.facing = 1; // 1 = right, -1 = left
        this.animFrame = 0;
        this.animTimer = 0;
        this.shooting = false;
        this.shootCooldown = 0;
        this.lives = 3;
        this.score = 0;
    }

    update(dt) {
        // Horizontal movement
        if (keys.ArrowLeft || keys.KeyA || touchControls.left) {
            this.vx = -MOVE_SPEED;
            this.facing = -1;
        } else if (keys.ArrowRight || keys.KeyD || touchControls.right) {
            this.vx = MOVE_SPEED;
            this.facing = 1;
        } else {
            this.vx = 0;
        }

        // Jump
        if ((keys.ArrowUp || keys.KeyW || keys.Space || keys.KeyZ || touchControls.jump) && this.onGround) {
            this.vy = JUMP_FORCE;
            this.onGround = false;
        }

        // Shoot bubble
        if ((keys.KeyX || touchControls.shoot) && this.shootCooldown <= 0) {
            this.shootBubble();
            this.shootCooldown = 20;
        }
        if (this.shootCooldown > 0) this.shootCooldown--;

        // Apply gravity
        this.vy += GRAVITY;

        // Update position
        this.x += this.vx;
        this.y += this.vy;

        // Platform collision
        this.onGround = false;
        for (let plat of platforms) {
            if (this.collidesWith(plat)) {
                // Determine collision side
                const overlapLeft = (this.x + this.width) - plat.x;
                const overlapRight = (plat.x + plat.w) - this.x;
                const overlapTop = (this.y + this.height) - plat.y;
                const overlapBottom = (plat.y + plat.h) - this.y;

                const minOverlapX = Math.min(overlapLeft, overlapRight);
                const minOverlapY = Math.min(overlapTop, overlapBottom);

                if (minOverlapY < minOverlapX) {
                    if (overlapTop < overlapBottom && this.vy > 0) {
                        this.y = plat.y - this.height;
                        this.vy = 0;
                        this.onGround = true;
                    } else if (this.vy < 0) {
                        this.y = plat.y + plat.h;
                        this.vy = 0;
                    }
                } else {
                    if (overlapLeft < overlapRight) {
                        this.x = plat.x - this.width;
                    } else {
                        this.x = plat.x + plat.w;
                    }
                    this.vx = 0;
                }
            }
        }

        // Screen wrap
        if (this.x < -this.width) this.x = GAME_WIDTH;
        if (this.x > GAME_WIDTH) this.x = -this.width;
        // Vertical wrap - fall off bottom, appear at top
        if (this.y > GAME_HEIGHT) {
            this.y = -this.height;
            this.vy = 0;
        }

        // Animation
        this.animTimer++;
        if (this.animTimer > 8) {
            this.animTimer = 0;
            this.animFrame = (this.animFrame + 1) % 4;
        }
    }

    shootBubble() {
        const bubble = new Bubble(
            this.x + (this.facing > 0 ? this.width : -20),
            this.y + this.height / 2 - 10,
            this.facing
        );
        bubbles.push(bubble);
    }

    collidesWith(rect) {
        return this.x < rect.x + rect.w &&
               this.x + this.width > rect.x &&
               this.y < rect.y + rect.h &&
               this.y + this.height > rect.y;
    }

    draw() {
        ctx.save();
        ctx.translate(this.x + this.width / 2, this.y + this.height / 2);
        if (this.facing < 0) ctx.scale(-1, 1);

        // Draw player (green dragon-like character)
        ctx.fillStyle = '#0c0';
        ctx.fillRect(-this.width / 2, -this.height / 2, this.width, this.height);

        // Eyes
        ctx.fillStyle = '#fff';
        ctx.fillRect(-this.width / 2 + 25, -this.height / 2 + 10, 12, 12);
        ctx.fillStyle = '#000';
        ctx.fillRect(-this.width / 2 + 30, -this.height / 2 + 14, 6, 6);

        // Animation bounce
        const bounce = Math.sin(this.animFrame * Math.PI / 2) * 3;
        ctx.fillStyle = '#0a0';
        ctx.fillRect(-this.width / 2 + 5, -this.height / 2 + 35 + bounce, 15, 10);
        ctx.fillRect(-this.width / 2 + 28, -this.height / 2 + 35 + bounce, 15, 10);

        ctx.restore();
    }
}

// Enemy class
class Enemy {
    constructor(x, y) {
        this.x = x;
        this.y = y;
        this.width = 48;
        this.height = 48;
        this.vx = (Math.random() > 0.5 ? 1 : -1) * 2;
        this.vy = 0;
        this.onGround = false;
        this.trapped = false;
        this.trapTimer = 0;
        this.animFrame = 0;
        this.animTimer = 0;
    }

    update(dt) {
        if (this.trapped) {
            this.trapTimer++;
            // Float upward when trapped
            this.vy = -1;
            this.y += this.vy;
            if (this.y < 30) this.y = 30;
            return;
        }

        // Simple AI: walk and change direction at walls/edges
        this.x += this.vx;

        // Apply gravity
        this.vy += GRAVITY;
        this.y += this.vy;

        // Platform collision
        this.onGround = false;
        for (let plat of platforms) {
            if (this.collidesWith(plat)) {
                const overlapTop = (this.y + this.height) - plat.y;
                const overlapBottom = (plat.y + plat.h) - this.y;

                if (overlapTop < overlapBottom && this.vy > 0) {
                    this.y = plat.y - this.height;
                    this.vy = 0;
                    this.onGround = true;
                }

                // Wall collision - reverse direction
                const overlapLeft = (this.x + this.width) - plat.x;
                const overlapRight = (plat.x + plat.w) - this.x;
                if (Math.min(overlapLeft, overlapRight) < 20) {
                    this.vx *= -1;
                }
            }
        }

        // Screen wrap
        if (this.x < -this.width) this.x = GAME_WIDTH;
        if (this.x > GAME_WIDTH) this.x = -this.width;

        // Random jump
        if (this.onGround && Math.random() < 0.01) {
            this.vy = JUMP_FORCE * 0.7;
        }

        // Animation
        this.animTimer++;
        if (this.animTimer > 10) {
            this.animTimer = 0;
            this.animFrame = (this.animFrame + 1) % 2;
        }
    }

    collidesWith(rect) {
        return this.x < rect.x + rect.w &&
               this.x + this.width > rect.x &&
               this.y < rect.y + rect.h &&
               this.y + this.height > rect.y;
    }

    draw() {
        if (this.trapped) {
            // Draw trapped in bubble
            ctx.fillStyle = 'rgba(100, 200, 255, 0.5)';
            ctx.beginPath();
            ctx.arc(this.x + this.width / 2, this.y + this.height / 2, 30, 0, Math.PI * 2);
            ctx.fill();
            ctx.strokeStyle = 'rgba(150, 220, 255, 0.8)';
            ctx.lineWidth = 3;
            ctx.stroke();
        }

        // Purple monster
        ctx.fillStyle = this.trapped ? '#a060c0' : '#9030c0';
        ctx.fillRect(this.x, this.y, this.width, this.height);

        // Eyes
        ctx.fillStyle = '#fff';
        ctx.fillRect(this.x + 8, this.y + 12, 12, 12);
        ctx.fillRect(this.x + 28, this.y + 12, 12, 12);
        ctx.fillStyle = '#f00';
        ctx.fillRect(this.x + 12, this.y + 16, 6, 6);
        ctx.fillRect(this.x + 32, this.y + 16, 6, 6);

        // Feet animation
        const offset = this.animFrame * 4;
        ctx.fillStyle = '#7020a0';
        ctx.fillRect(this.x + 5 + offset, this.y + 40, 15, 8);
        ctx.fillRect(this.x + 28 - offset, this.y + 40, 15, 8);
    }
}

// Bubble class
class Bubble {
    constructor(x, y, dir) {
        this.x = x;
        this.y = y;
        this.radius = 20;
        this.vx = dir * BUBBLE_SPEED;
        this.vy = 0;
        this.life = 300;
        this.trappedEnemy = null;
        this.floating = false;
    }

    update() {
        this.life--;

        if (!this.floating) {
            this.x += this.vx;
            // Slow down and start floating
            this.vx *= 0.95;
            if (Math.abs(this.vx) < 0.5) {
                this.floating = true;
            }
        } else {
            // Float upward with slight wobble
            this.vy = -1;
            this.y += this.vy;
            this.x += Math.sin(this.life * 0.1) * 0.5;
        }

        // Check collision with enemies
        if (!this.trappedEnemy) {
            for (let enemy of enemies) {
                if (!enemy.trapped && this.collidesWithEnemy(enemy)) {
                    enemy.trapped = true;
                    this.trappedEnemy = enemy;
                    this.floating = true;
                }
            }
        } else {
            // Move trapped enemy with bubble
            this.trappedEnemy.x = this.x - this.trappedEnemy.width / 2 + this.radius;
            this.trappedEnemy.y = this.y - this.trappedEnemy.height / 2 + this.radius;
        }

        // Wall collision
        if (this.x < 20) { this.x = 20; this.vx *= -0.5; }
        if (this.x > GAME_WIDTH - 40) { this.x = GAME_WIDTH - 40; this.vx *= -0.5; }
        if (this.y < 20) { this.y = 20; this.vy = 0; }
    }

    collidesWithEnemy(enemy) {
        const cx = this.x + this.radius;
        const cy = this.y + this.radius;
        const nearestX = Math.max(enemy.x, Math.min(cx, enemy.x + enemy.width));
        const nearestY = Math.max(enemy.y, Math.min(cy, enemy.y + enemy.height));
        const dx = cx - nearestX;
        const dy = cy - nearestY;
        return (dx * dx + dy * dy) < (this.radius * this.radius);
    }

    collidesWithPlayer() {
        const cx = this.x + this.radius;
        const cy = this.y + this.radius;
        const nearestX = Math.max(player.x, Math.min(cx, player.x + player.width));
        const nearestY = Math.max(player.y, Math.min(cy, player.y + player.height));
        const dx = cx - nearestX;
        const dy = cy - nearestY;
        return (dx * dx + dy * dy) < (this.radius * this.radius);
    }

    draw() {
        ctx.fillStyle = 'rgba(100, 200, 255, 0.6)';
        ctx.beginPath();
        ctx.arc(this.x + this.radius, this.y + this.radius, this.radius, 0, Math.PI * 2);
        ctx.fill();

        ctx.strokeStyle = 'rgba(150, 230, 255, 0.9)';
        ctx.lineWidth = 2;
        ctx.stroke();

        // Shine
        ctx.fillStyle = 'rgba(255, 255, 255, 0.5)';
        ctx.beginPath();
        ctx.arc(this.x + this.radius - 8, this.y + this.radius - 8, 5, 0, Math.PI * 2);
        ctx.fill();
    }
}

// Fruit class
class Fruit {
    constructor(x, y) {
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
        this.vy = 0;
        this.onGround = false;
        this.points = 100;
    }

    update() {
        if (!this.onGround) {
            this.vy += GRAVITY * 0.5;
            this.y += this.vy;

            for (let plat of platforms) {
                if (this.collidesWith(plat)) {
                    this.y = plat.y - this.height;
                    this.vy = 0;
                    this.onGround = true;
                }
            }
        }
    }

    collidesWith(rect) {
        return this.x < rect.x + rect.w &&
               this.x + this.width > rect.x &&
               this.y < rect.y + rect.h &&
               this.y + this.height > rect.y;
    }

    collidesWithPlayer() {
        return player.x < this.x + this.width &&
               player.x + player.width > this.x &&
               player.y < this.y + this.height &&
               player.y + player.height > this.y;
    }

    draw() {
        // Yellow banana-like fruit
        ctx.fillStyle = '#fc0';
        ctx.beginPath();
        ctx.ellipse(this.x + 16, this.y + 16, 14, 10, Math.PI / 6, 0, Math.PI * 2);
        ctx.fill();
        ctx.fillStyle = '#a80';
        ctx.fillRect(this.x + 14, this.y + 2, 4, 6);
    }
}

// Initialize game
function initGame() {
    player = new Player(100, 400);
    enemies = [];
    bubbles = [];
    fruits = [];

    // Create platforms
    platforms = PLATFORM_DATA.map(p => ({ x: p.x, y: p.y, w: p.w, h: p.h }));

    // Spawn enemies
    enemies.push(new Enemy(600, 300));
    enemies.push(new Enemy(400, 200));
    enemies.push(new Enemy(200, 100));
    enemies.push(new Enemy(650, 100));
}

// Game loop
function update() {
    if (!gameStarted) return;

    // Update player
    player.update();

    // Update enemies
    for (let enemy of enemies) {
        enemy.update();

        // Check player collision with non-trapped enemy
        if (!enemy.trapped && player.collidesWith(enemy)) {
            // Player dies - reset position
            player.x = 100;
            player.y = 400;
            player.lives--;
            if (player.lives <= 0) {
                gameStarted = false;
                document.getElementById('startScreen').style.display = 'flex';
                document.getElementById('startScreen').querySelector('h1').textContent = 'GAME OVER';
                document.getElementById('startScreen').querySelector('p').textContent = 'Score: ' + player.score + ' - Click to Restart';
            }
        }
    }

    // Update bubbles
    for (let i = bubbles.length - 1; i >= 0; i--) {
        bubbles[i].update();

        // Pop bubble if player touches it and it has a trapped enemy
        if (bubbles[i].trappedEnemy && bubbles[i].collidesWithPlayer()) {
            // Remove enemy and create fruit
            const enemy = bubbles[i].trappedEnemy;
            fruits.push(new Fruit(enemy.x, enemy.y));
            enemies = enemies.filter(e => e !== enemy);
            bubbles.splice(i, 1);
            player.score += 100;
            continue;
        }

        // Remove expired bubbles
        if (bubbles[i].life <= 0) {
            if (bubbles[i].trappedEnemy) {
                bubbles[i].trappedEnemy.trapped = false;
            }
            bubbles.splice(i, 1);
        }
    }

    // Update fruits
    for (let i = fruits.length - 1; i >= 0; i--) {
        fruits[i].update();

        if (fruits[i].collidesWithPlayer()) {
            player.score += fruits[i].points;
            fruits.splice(i, 1);
        }
    }

    // Check win condition
    if (enemies.length === 0) {
        // Spawn more enemies
        enemies.push(new Enemy(600, 300));
        enemies.push(new Enemy(400, 200));
        enemies.push(new Enemy(200, 100));
        enemies.push(new Enemy(650, 100));
        player.score += 500;
    }
}

function draw() {
    // Clear canvas
    ctx.fillStyle = '#141428';
    ctx.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

    if (!gameStarted) return;

    // Draw platforms
    ctx.fillStyle = '#654';
    for (let plat of platforms) {
        ctx.fillRect(plat.x, plat.y, plat.w, plat.h);
        // Brick pattern
        ctx.strokeStyle = '#543';
        ctx.lineWidth = 1;
        for (let bx = plat.x; bx < plat.x + plat.w; bx += 20) {
            ctx.beginPath();
            ctx.moveTo(bx, plat.y);
            ctx.lineTo(bx, plat.y + plat.h);
            ctx.stroke();
        }
    }

    // Draw fruits
    for (let fruit of fruits) {
        fruit.draw();
    }

    // Draw bubbles
    for (let bubble of bubbles) {
        bubble.draw();
    }

    // Draw enemies
    for (let enemy of enemies) {
        enemy.draw();
    }

    // Draw player
    player.draw();

    // Draw HUD
    ctx.fillStyle = '#fff';
    ctx.font = '16px monospace';
    ctx.fillText('Score: ' + player.score, 30, 50);
    ctx.fillText('Lives: ' + player.lives, GAME_WIDTH - 120, 50);
}

function gameLoop() {
    update();
    draw();
    requestAnimationFrame(gameLoop);
}

// Input handling
document.addEventListener('keydown', (e) => {
    keys[e.code] = true;
    if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'Space'].includes(e.code)) {
        e.preventDefault();
    }
});

document.addEventListener('keyup', (e) => {
    keys[e.code] = false;
});

// Touch controls
function setupTouchControls() {
    const dpadUp = document.getElementById('dpad-up');
    const dpadDown = document.getElementById('dpad-down');
    const dpadLeft = document.getElementById('dpad-left');
    const dpadRight = document.getElementById('dpad-right');
    const btnA = document.getElementById('btn-a');
    const btnB = document.getElementById('btn-b');

    const addTouchEvents = (el, control) => {
        el.addEventListener('touchstart', (e) => { e.preventDefault(); touchControls[control] = true; });
        el.addEventListener('touchend', (e) => { e.preventDefault(); touchControls[control] = false; });
        el.addEventListener('touchcancel', (e) => { touchControls[control] = false; });
    };

    addTouchEvents(dpadUp, 'jump');
    addTouchEvents(dpadLeft, 'left');
    addTouchEvents(dpadRight, 'right');
    addTouchEvents(btnA, 'jump');
    addTouchEvents(btnB, 'shoot');
}

// Start game
document.getElementById('startScreen').addEventListener('click', () => {
    document.getElementById('startScreen').style.display = 'none';
    document.getElementById('controls').classList.add('show');
    document.getElementById('instructions').classList.add('show');
    initGame();
    gameStarted = true;
});

// Responsive canvas
function resizeCanvas() {
    const container = document.getElementById('gameContainer');
    const maxWidth = window.innerWidth;
    const maxHeight = window.innerHeight;

    scale = Math.min(maxWidth / GAME_WIDTH, maxHeight / GAME_HEIGHT);

    canvas.width = GAME_WIDTH;
    canvas.height = GAME_HEIGHT;
    canvas.style.width = (GAME_WIDTH * scale) + 'px';
    canvas.style.height = (GAME_HEIGHT * scale) + 'px';
}

window.addEventListener('resize', resizeCanvas);
resizeCanvas();
setupTouchControls();
gameLoop();
