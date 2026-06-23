// 在 __tests__/stores/user.test.js 中
import { setActivePinia, createPinia } from 'pinia'
import { useUserStore } from '@/stores/user'
import { login } from '@/api/auth'
import { vi } from 'vitest'

vi.mock('@/api/auth', () => ({
  login: vi.fn()
}))

describe('User Store', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it('login action sets token', async () => {
    login.mockResolvedValue({ data: { token: 'abc', role: 'user' } })
    const store = useUserStore()
    await store.loginAction({ username: 'test', password: '123' })
    expect(store.token).toBe('abc')
    expect(store.role).toBe('user')
  })
})